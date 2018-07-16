var ItemEditor = function(){
  this.meals = []
  this.menu = {id: null, local_id: Utils.genUUID(), name: "", meal: null, menuSections: []};
  this.sectionTypes = {"breakfast": "Breakfast Items", "sandwiches-salads": "Salads & Sandwiches", "beverages": "Beverages", "snacks": "Snacks"};
  
  this.refreshMenu().then(function(){
    this.buildMenuForm(this.menu);
  }.bind(this))
}

ItemEditor.prototype.refreshMenu = function(menu_id){
  return Promise.all([
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
          url: "/admin/api/menu/",
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

ItemEditor.prototype.initMove = function(menu_id, item_id, e){      
  // // e.preventDefault();
  // e.stopPropagation();

  // var me = this;

  // var onDrag = function(e){
  //   if (e.screenY === 0) return;

  //   var list    = document.querySelector(".menu-" + menu_id),
  //       items   = list.querySelectorAll(".menu-item"),
  //       scroll  = window.scrollY || document.documentElement.scrollTop,
  //       coords  = list.getBoundingClientRect()
  //       boxTop  = coords.top,
  //       boxBtm  = coords.top + coords.height,
  //       eTop    = e.pageY - (scroll + boxTop),
  //       eBtm    = e.pageY - (scroll + boxBtm),
  //       tops    = Array.prototype.map.call(items, function(item){ return [item.offsetTop + (item.offsetHeight / 2), item.dataset.id] });
    
  //   var sibling = null;    
  //   for (var i=0; i<tops.length; i++){
  //     if (i === 0){
  //       if (eTop < tops[0][0]){
  //         sibling = document.querySelector(".menu-" + menu_id + " .menu-item-" + tops[0][1]);
  //         break;
  //       }
  //     } else {
  //       if (tops[i - 1][0] < eTop && tops[i][0] > eTop){
  //         sibling = document.querySelector(".menu-" + menu_id + " .menu-item-" + tops[i][1]);
  //         break;
  //       }
  //     }
  //   }

  //   if (sibling !== null){
  //     if (sibling.dataset.id !== item_id){
  //       sibling.parentNode.insertBefore(dragTarget, sibling);
  //     }
  //   } else {
  //     if (tops[tops.length - 1][1] !== item_id){
  //       dragTarget.parentNode.appendChild(dragTarget)
  //     }
  //   }
  // }

  // var onDragEnd = function(){
  //   dragTarget.removeEventListener("drag", onDrag);
  //   dragTarget.removeEventListener("dragend", onDragEnd);
  
  //   var menu = me.menus.filter(function(menu){ return menu.id === menu_id })[0];
  //       list = document.querySelectorAll(".menu-" + menu_id + " .menu-item"),
  //       ordering = Array.prototype.map.call(list, function(item){ return item.dataset.id }).join(",");

  //   menu.ordering = ordering;
  //   me.saveMenu(menu);
  // }
      

  // var dragHandle = document.querySelector(".menu-" + menu_id + " .menu-item-" + item_id + " .menu-item-move"),
  //     dragTarget = document.querySelector(".menu-" + menu_id + " .menu-item-" + item_id);
  
  // dragTarget.draggable = "true";
  // dragTarget.addEventListener("drag", onDrag, false);
  // dragTarget.addEventListener("dragend", onDragEnd, false);
}

ItemEditor.prototype.openEditMenuItem = function(){

}

ItemEditor.prototype.removeMenuItem = function(menu_id, item_id) {
  var menu = this.menus.filter(function(menu){ return menu.id === menu_id })[0],
      ordering = menu.ordering.split(","),
      idx = ordering.indexOf(item_id.toString());

  ordering.splice(idx, 1);
  menu.ordering = ordering.join(",");

  this.saveMenu(menu);
}

ItemEditor.prototype.addItem = function(section_id) {
  var section = this.sections.filter(function(s){ return s.id === section_id || s.local_id === section_id })[0],
      name = document.querySelector(".menu-" + section_id + " .add-item-input").value,
      item = {id: null, local_id: Utils.genUUID(), name: name};

  var ordering = section.ordering.split(",");
      ordering.push(item.local_id);

  section.menuItems.push(item)
  section.ordering = ordering.join(",");
  // $.ajax({
  //   url: "/admin/api/menu-items/save",
  //   type: "POST",
  //   contentType: "application/json",
  //   data: JSON.stringify({id: null, name: name, menus: [menu]}),
  //   success: function(resp){
  //     if (!resp.errors){
  //       this.items.push(resp);

  //       var ids = (menu.ordering || "").split(",").map(function(id){ return parseInt(id, 10) || "" }).filter(function(id){ return !!id });
  //           ids.push(resp.id);
        
  //       menu.ordering = ids.join(",");
  //       this.saveMenu(menu);
  //     } else {
  //       console.log(resp.errors)
  //     }
  //   }.bind(this),
  //   error: function(resp){

  //   }.bind(this)
  // })
}

ItemEditor.prototype.saveMenu = function(menu) {
  $.ajax({
    url: "/admin/api/menus/save",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(menu),
    
    success: function(resp){
      this.menu = resp;
      this.buildMenu(resp);
    }.bind(this),
    error: function(resp){

    }.bind(this)
  })
}

ItemEditor.prototype.buildMenuForm = function(menu) {
  // var items = (section.ordering || "").split(",").map(function(id){ return this.items.filter(function(item){ return item.id === parseInt(id, 10) })[0] }.bind(this)).filter(function(item){ return !!item }) || [],
  var parent = document.querySelector(".menus"),
      wrapper = document.querySelector(".menu-" + (menu.id || menu.local_id));

  var struct = {tag: "form", attributes: {class: "menu-form"}, children: [
    {tag: "div", attributes: {className: "input-wrapper name"}, children: [
      {tag: "h2", attributes: {text: "Menu Name"}},
      {tag: "input", attributes: {type: "text", value: menu.name || ""}}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Meal"}},
      {tag: "div", attributes: {className: "options"}, children: this.meals.map(function(meal, index){
        return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", name: "status", value: meal.id, id: "meal-" + index}},
          {tag: "label", attributes: {className: 'btn', text: "Active", "for": "meal-" + index}} 
        ]}
      })}
    ]},
    {tag: "ul", attributes: {className: "sortable-lists sections"}, children: Object.keys(this.sectionTypes).map(function(key){
      var section = this.menu.menuSections.filter(function(s){ s.name === key })[0],
          label = this.sectionTypes[key];

      if (!section){
        section = {id: null, local_id: Utils.genUUID(), name: key, ordering: "", menuItems: []}
        this.menu.menuSections.push(section);
      }

      

      return {tag: "li", attributes: {className: " sortable-list section"}, children: [
        {tag: "h2", attributes: {text: label}},
        {tag: "ul", attributes: {className: "sortable-list-items menu-items"}, children: (section.menuItems || []).map(function(menuItem){
          return {tag: "li", attributes: {className: " sortable-list-item menu-item menu-item-" + (menuItem.id || menuItem.local_id), dataId: (menuItem.id || menuItem.local_id)}, children: [
            {tag: "i", attributes: {className: "fa fa-arrows menu-item-move", onMouseDown: this.initMove.bind(this, menu, menuItem)}, children: []},
            {tag: "div", attributes: {className: "menu-item-name", text: menuItem.name}, children: []},
            {tag: "i", attributes: {className: "fa fa-edit menu-item-edit", onClick: this.openEditMenuItem.bind(this, menu, menuItem)}, children: []},
            {tag: "i", attributes: {className: "fa fa-trash-o menu-item-remove", onClick: this.removeMenuItem.bind(this, menu, menuItem), children: []}}
          ]}
        })},
        {tag: "div", attributes: {className: "add-item-wrapper"}, children: [
          {tag: "div", attributes: {className: "input-wrapper"}, children: [
            {tag: "input", attributes: {className: "add-item-input", type: "text"}, children: []}
          ]},
          {tag: "div", attributes: {className: "input-wrapper"}, children: [
            {tag: "input", attributes: {className: "add-item-btn btn", value: "Add", type: "button", onClick: this.addItem.bind(this, menu)}}
          ]}
        ]}
      ]}
    }.bind(this))}
  ]}

  var html = Utils.buildHTML(struct);
  
  if (wrapper !== null){
    wrapper.parentNode.insertBefore(html, wrapper);
    wrapper.parentNode.removeChild(wrapper);
  } else {
    parent.appendChild(html)
  }
}

// ItemEditor.prototype.buildMenuForm = function(menu) {
//   var items = (section.ordering || "").split(",").map(function(id){ return this.items.filter(function(item){ return item.id === parseInt(id, 10) })[0] }.bind(this)).filter(function(item){ return !!item }) || [],
//       parent = document.querySelector(".menus"),
//       wrapper = document.querySelector(".menu-" + section.name);

//   var struct = {tag: "li", attributes: {className: "menu menu-" + section.name }, children: [
//     {tag: "h2", attributes: {text: menu.name}, children: []},
//     {tag: "p", attributes: {text: "Leave this section empty to hide it for the meal"}},
//     {tag: "ul", attributes: {className: "menu-items"}, children: items.map(function(menuItem, j){
//       return {tag: "li", attributes: {className: "menu-item menu-item-" + menuItem.id, dataId: menuItem.id}, children: [
//         {tag: "i", attributes: {className: "fa fa-arrows menu-item-move", onMouseDown: this.initMove.bind(this, menu.id, menuItem.id)}, children: []},
//         {tag: "div", attributes: {className: "menu-item-name", text: menuItem.name}, children: []},
//         {tag: "i", attributes: {className: "fa fa-edit menu-item-edit", onClick: this.openEditMenuItem.bind(this, menu.id, menuItem.id)}, children: []},
//         {tag: "i", attributes: {className: "fa fa-trash-o menu-item-remove", onClick: this.removeMenuItem.bind(this, menu.id, menuItem.id), children: []}}
//       ]}
//     }.bind(this))},
//     {tag: "div", attributes: {className: "add-item-wrapper"}, children: [
//       {tag: "div", attributes: {className: "input-wrapper"}, children: [
//         {tag: "input", attributes: {className: "add-item-input", type: "text"}, children: []}
//       ]},
//       {tag: "div", attributes: {className: "input-wrapper"}, children: [
//         {tag: "input", attributes: {className: "add-item-btn btn", value: "Add", type: "button", onClick: this.addItem.bind(this, menu.id)}}
//       ]}
//     ]}
//   ]}

//   var html = Utils.buildHTML(struct);
  
//   if (wrapper !== null){
//     wrapper.parentNode.insertBefore(html, wrapper);
//     wrapper.parentNode.removeChild(wrapper);
//   } else {
//     parent.appendChild(html)
//   }
// }

// ItemEditor.prototype.buildMenus = function() {
//   for (var x in this.sectionTypes){
//     var section = this.sections.filter(function(s){ s.name === x})[0] || {name: x, ordering: "", menu: this.menu}
//     this.buildMenu(section);
//   } 
// }
    