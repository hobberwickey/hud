var ItemEditor = function(){
  this.menus = []
  this.meals = []
  this.edits = []
  this.options = []
  this.menu = {id: null, localId: Utils.genUUID(), name: "", meal: null, menuSections: []};
  this.sectionTypes = {"breakfast": "Breakfast Items", "sandwiches-salads": "Salads & Sandwiches", "beverages": "Beverages", "snacks": "Snacks"};
}

ItemEditor.prototype.refreshMenus = function(){
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/menus/",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.menus = resp
        res()
      }.bind(this),
      error: function(err) {
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

ItemEditor.prototype.refreshMenu = function(menu_id){
  return Promise.all([
    new Promise(function(res, rej){
      $.ajax({
        url: "/admin/api/menu-item-options/",
        type: "GET",
        contentType: "application/json",
        success: function(resp) {
          this.options = resp
          res()
        }.bind(this),
        error: function(err) {
          rej(err)
        }.bind(this)
      })
    }.bind(this)),

    new Promise(function(res, rej){
      $.ajax({
        url: "/admin/api/meals/",
        type: "GET",
        contentType: "application/json",
        success: function(resp) {
          this.meals = resp
          res()
        }.bind(this),
        error: function(err) {
          rej(err)
        }.bind(this)
      })
    }.bind(this)),

    new Promise(function(res, rej){
      if (!menu_id){
        res()
      } else {
        $.ajax({
          url: "/admin/api/menus/" + menu_id,
          type: "GET",
          contentType: "application/json",
          success: function(resp) {
            this.menu = resp
            res()
          }.bind(this),
          error: function(err) {
            rej(err)
          }.bind(this)
        })
      }
    }.bind(this))  
  ])
}

ItemEditor.prototype.saveMenu = function(menu) {
  console.log(menu)
  
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/menus/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(menu),
      
      success: function(resp){
        window.location = "/admin/menus"
        // if (window.location.pathname.split("/")[3] === "new" && !!resp.id){
        //   window.location.assign("/admin/menus/" + resp.id);
        // } else {
        //   this.menu = resp;
        //   this.buildMenuForm(this.menu);
        // }
        // res()
      }.bind(this),
      error: function(resp){
        rej()
      }.bind(this)
    })
  }.bind(this))
}

ItemEditor.prototype.updateMenu = function(key, e) {
  this.menu[key] = e.target.value;
}

ItemEditor.prototype.updateMeal = function(e) {
  var meal = this.meals.filter(function(m){ return m.id === parseInt(e.target.value, 10) })[0];

  if (!!meal){
    this.menu.meal = meal;
  }
}

ItemEditor.prototype.submitForm = function(e){
  e.stopPropagation();
  e.preventDefault();

  this.saveMenu(this.menu)
}

ItemEditor.prototype.initMove = function(section, item, e){      
  Utils.initMove("section", "menu-item", (section.id || section.localId), (item.id || item.localId), this.reorder.bind(this, section), e)
}

ItemEditor.prototype.openEditMenuItem = function(section, item){
  this.edits.push(item.id || item.localId);
  this.buildMenuSection(section.name, true)

  document.querySelector(".menu-item-" + (item.id || item.localId) + " input").select();
}

ItemEditor.prototype.editMenuItem = function(section, item){
  var input = document.querySelector(".menu-item-" + item.id + " input");

  if (!!input) {
    item.name = input.value;
    this.edits.splice(this.edits.indexOf(item.id || item.localId), 1);
    this.buildMenuSection(section.name, true);
  }
}

ItemEditor.prototype.cancelEditMenuItem = function(section, item){
  this.edits.splice(this.edits.indexOf(item.id || item.localId), 1);
  this.buildMenuSection(section.name, true)
}

ItemEditor.prototype.toggleGroup = function(section, item, group, e) {
  var existing = item.menuItemOptionGroups.filter(function(g){ return g.id === group.id })[0]

  if (!!existing){
    item.menuItemOptionGroups.splice(item.menuItemOptionGroups.indexOf(existing), 1)
  } else {
    item.menuItemOptionGroups.push(group)
  }

  this.buildMenuSection(section.name, true)
}

ItemEditor.prototype.hasGroup = function(item, group) {
  return !!item.menuItemOptionGroups && !!item.menuItemOptionGroups.filter(function(g){ return g.id === group.id })[0];
}

ItemEditor.prototype.rebuildOrdering = function(section){
  var list = document.querySelectorAll(".section-" + (section.id || section.localId) + " .menu-item");

  if (!!section){
    var ordering = Array.prototype.map.call(list, function(el){ return isNaN(el.dataset.id, 10) ? el.dataset.id : parseInt(el.dataset.id, 10) });

    console.log(ordering)

    for (var i=0; i<(section.menuItems || []).length; i++){ 
      var item = section.menuItems[i];
          item.position = ordering.indexOf(item.id || item.localId);

      console.log(item.id || item.localId, item.position)
    }

    section.ordering = ordering.join(",");
  }
}

ItemEditor.prototype.reorder = function(section){
  if (!!section){
    this.rebuildOrdering(section);
    // this.buildMenuSection(section.name, true);
  }
}

ItemEditor.prototype.removeMenuItem = function(section, item) {
  var ordering = section.ordering.split(","),
      idx = ordering.indexOf((item.id + "")  || item.localId);
  
  section.menuItems.splice(section.menuItems.indexOf(item), 1)
  section.ordering = ordering.join(",");

  this.buildMenuSection(section.name, true)
}

ItemEditor.prototype.addItem = function(section) {
  var name = document.querySelector(".section-" + (section.id || section.localId) + " .add-item-input").value,
      item = {id: null, localId: Utils.genUUID(), name: name, position: section.menuItems.length};

  section.menuItems.push(item)
  
  // section.ordering = (section.ordering || "").split(",").concat([item.localId]).filter(function(id){ return !!id}).join(",");
  // this.rebuildOrdering(section);
  this.buildMenuSection(section.name, true);
}

ItemEditor.prototype.buildMenuSection = function(key, replace) {
  var section = this.menu.menuSections.filter(function(s){ return s.name === key })[0],
      label = this.sectionTypes[key];

  if (!section){
    var sectionOrdering = ["breakfast", "sandwiches-salads", "beverages", "snacks"]
        section = {id: null, localId: Utils.genUUID(), name: key, position: sectionOrdering.indexOf(key), ordering: "", menuItems: []}
    this.menu.menuSections.push(section);
  }

  var struct = {tag: "li", attributes: {className: "sortable-list section section-" + (section.id || section.localId), dataId: (section.id || section.localId)}, children: [
    {tag: "h2", attributes: {text: label}},
    {tag: "ul", attributes: {className: "sortable-list-items menu-items menu-list"}, children: section.menuItems.sort(function(a, b){ return a.position > b.position }).map(function(menuItem){
      // var menuItem = section.menuItems.filter(function(item){ return (parseInt(item.id, 10) === parseInt(id, 10) || item.localId === id) })[0];

      if (!!menuItem){
        var editClass = this.edits.indexOf(menuItem.id || menuItem.localId) === -1 ? "" : "editing";

        return {tag: "li", attributes: {className: editClass + " sortable-list-item menu-item menu-item-" + (menuItem.id || menuItem.localId), dataId: (menuItem.id || menuItem.localId)}, children: [
          {tag: "i", attributes: {className: "fa fa-arrows move", onMouseDown: this.initMove.bind(this, section, menuItem)}, children: []},
          {tag: "div", attributes: {className: "menu-item-name", text: menuItem.name}, children: []},
          {tag: "div", attributes: {className: "item-editor"}, children: [
            {tag: "input", attributes: {className: "item-edit", type: "text", value: menuItem.name}},
            {tag: "div", attributes: {className: "item-edit-controls"}, children: [
              {tag: "i", attributes: {className: "fa fa-check confirm", onClick: this.editMenuItem.bind(this, section, menuItem)}},
              {tag: "i", attributes: {className: "fa fa-times cancel", onClick: this.cancelEditMenuItem.bind(this, section, menuItem)}}
            ]}
          ]},
          {tag: "div", attributes: {className: "actions"}, children: [
            {tag: "i", attributes: {className: "fa fa-edit edit", onClick: this.openEditMenuItem.bind(this, section, menuItem)}, children: []},
            {tag: "i", attributes: {className: "fa fa-trash-o remove", onClick: this.removeMenuItem.bind(this, section, menuItem), children: []}}
          ]},
          {tag: "div", attributes: {className: "menu-item-options"}, children: this.options.map(function(group) {
            var activeClass = this.hasGroup(menuItem, group) ? "active " : "";
            return {tag: "i", attributes: {className: activeClass + "fa fa-check", onClick: this.toggleGroup.bind(this, section, menuItem, group)}}
          }.bind(this))}
        ]}
      } else {
        return null
      }
    }.bind(this)).filter(
      function(item){ return !!item}.bind(this))
    },
    {tag: "div", attributes: {className: "add-item-wrapper"}, children: [
      {tag: "div", attributes: {className: "input-wrapper"}, children: [
        {tag: "input", attributes: {className: "add-item-input", type: "text"}, children: []}
      ]},
      {tag: "div", attributes: {className: "input-wrapper"}, children: [
        {tag: "input", attributes: {className: "add-item-btn btn", value: "Add", type: "button", onClick: this.addItem.bind(this, section)}}
      ]}
    ]}
  ]}

  if (replace){
    var parent = document.querySelector(".sections"),
        wrapper = parent.querySelector(".section-" + (section.id || section.localId));

    var html = Utils.buildHTML(struct);
  
    if (wrapper !== null){
      wrapper.parentNode.insertBefore(html, wrapper);
      wrapper.parentNode.removeChild(wrapper);
    } else {
      parent.appendChild(html)
    }
  } else {
    return struct;
  }
}

ItemEditor.prototype.buildMenuForm = function(menu) {
  // var items = (section.ordering || "").split(",").map(function(id){ return this.items.filter(function(item){ return item.id === parseInt(id, 10) })[0] }.bind(this)).filter(function(item){ return !!item }) || [],
  var parent = document.querySelector(".menus"),
      wrapper = document.querySelector(".menus form");

  var struct = {tag: "form", attributes: {class: "menu-form", onSubmit: this.submitForm.bind(this)}, children: [
    {tag: "div", attributes: {className: "input-wrapper name"}, children: [
      {tag: "h2", attributes: {text: "Menu Name"}},
      {tag: "input", attributes: {type: "text", value: menu.name || "", onChange: this.updateMenu.bind(this, "name") }}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Meal"}},
      {tag: "div", attributes: {className: "options"}, children: this.meals.map(function(meal, index){
        var checked = menu.meal && menu.meal.id === meal.id

        return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", name: "meal", checked: checked, value: meal.id, id: "meal-" + index, onChange: this.updateMeal.bind(this)}},
          {tag: "label", attributes: {className: 'btn', text: meal.name, "for": "meal-" + index}} 
        ]}
      }.bind(this))}
    ]},
    {tag: "ul", attributes: {className: "sortable-lists sections"}, children: Object.keys(this.sectionTypes).map(function(key){
      return this.buildMenuSection(key, false)
    }.bind(this))},
    {tag: "div", attributes: {className: "btns"}, children: [
      {tag: "input", attributes: {className: "btn submit confirm", type: "submit"}, children: []},
      {tag: "a", attributes: {className: "btn cancel", href: "/admin/menus", text: "Cancel"}, children: []}
    ]}
  ]}

  var html = Utils.buildHTML(struct);
  
  if (wrapper !== null){
    wrapper.parentNode.insertBefore(html, wrapper);
    wrapper.parentNode.removeChild(wrapper);
  } else {
    parent.appendChild(html)
  }
}

ItemEditor.prototype.buildMenus = function() {
  for (var i=0; i<this.menus.length; i++){
    var menu = this.menus[i],
        parent = document.querySelector(".menus"),
        wrapper = document.querySelector(".menus-" + menu.id);

    var struct = {tag: "li", attributes: {className: "info-list menu-" + menu.id}, children: [
      {tag: "div", attributes: {className: "info name", text: menu.name}, children: []},
      {tag: "div", attributes: {className: "action"}, children: [
        {tag: "a", attributes: {href: "/admin/menus/" + menu.id}, children: [
          {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
        ]}
      ]},
      {tag: "div", attributes: {className: "action"}, children: [
        {tag: "a", attributes: {href: "/admin/users/" + menu.id + "/delete"}, children: [
          {tag: "i", attributes: {className: "fa fa-trash-o"}, children: []}
        ]}
      ]}
    ]}
    
    var html = Utils.buildHTML(struct);
  
    if (wrapper !== null){
      wrapper.parentNode.insertBefore(html, wrapper);
      wrapper.parentNode.removeChild(wrapper);
    } else {
      parent.appendChild(html)
    }
  }
}
    