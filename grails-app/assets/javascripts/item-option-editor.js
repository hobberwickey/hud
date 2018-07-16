var ItemOptionEditor = function(){
  this.groups = [];
  // this.refreshLocations();
}


ItemOptionEditor.prototype.refreshOptions = function(){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/menu-item-options",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.groups = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

ItemOptionEditor.prototype.saveOption = function(option){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/menu-item-options/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(option),
      success: function(resp) {
        // this.groups = resp;
        res(resp);
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

ItemOptionEditor.prototype.saveGroup = function(group){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/menu-item-options/ordering",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(group),
      success: function(resp) {
        // this.groups = resp;
        res(resp);
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

ItemOptionEditor.prototype.addItem = function(group_id) {
  var group = this.groups.filter(function(g){ return parseInt(g.id, 10) === parseInt(group_id, 10)})[0],
      input = document.querySelector(".group-" + group_id + " .add-item-input"),
      value = input.value;

  if (!group){
    console.log("Couldn't find group with id: ", group_id)
    return
  }

  var option = {
    id: null,
    name: value,
    menuItemOptionGroups: [{id: group_id}]
  }

  this.saveOption(option).then(function(saved) {
    group.menuItemOptions.push(saved);
    group.ordering = group.ordering.split(",").concat([saved.id]).filter(function(id){ return !!id}).join(",");
    
    this.saveGroup(group).then(function(savedGroup) {
      this.buildOptionGroup(savedGroup);
    }.bind(this));
  }.bind(this))
}

ItemOptionEditor.prototype.saveOrdering = function(group_id) {
  var group = this.groups.filter(function(g){ return parseInt(g.id, 10) === parseInt(group_id, 10)})[0],
      list = document.querySelectorAll(".group-" + group_id + " .group-item");

  if (!!group){
    group.ordering = Array.prototype.map.call(list, function(el){ return el.dataset.id }).join(",");
    this.saveGroup(group);
  }
}

ItemOptionEditor.prototype.openEditOption = function(group_id, option_id) {
  console.log(group_id, option_id)
}

ItemOptionEditor.prototype.removeOption = function(group_id, option_id) {
  var group = this.groups.filter(function(g){ return parseInt(g.id, 10) === parseInt(group_id, 10)})[0],
      option = group.menuItemOptions.filter(function(o) { return o.id === parseInt(option_id, 10)})[0];

  if (!!group && !!option){
    var ids = group.ordering.split(",").map(function(id){ return parseInt(id, 10)})
        ids.splice(ids.indexOf(parseInt(option_id, 10)), 1);

    group.ordering = ids.join(",");
    option.deleted = true;

    this.saveGroup(group).then(function(savedGroup) {
      this.buildOptionGroup(savedGroup);
    }.bind(this));
  }
}

ItemOptionEditor.prototype.buildOptionGroup = function(group) {
  var parent = document.querySelector(".groups"),
      wrapper = parent.querySelector(".group-" + group.id);

  var struct = {tag: "li", attributes: {className: "sortable-list group group-" + group.id }, children: [
    {tag: "h2", attributes: {text: group.name}, children: []},
    {tag: "ul", attributes: {className: "sortable-list-items group-items"}, children: (group.ordering.split(",") || []).map(function(id, j){
      var option = group.menuItemOptions.filter(function(option){ return parseInt(id, 10) === option.id })[0];
      if (!option) {
        return {tag: "li", attributes: {className: "empty"}}
      } else {
        var move = Utils.initMove.bind(null, "group", "group-item", group.id, option.id, this.saveOrdering.bind(this, group.id))

        return {tag: "li", attributes: {className: "sortable-list-item group-item group-item-" + option.id, dataId: option.id}, children: [
          {tag: "i", attributes: {className: "fa fa-arrows group-item-move", onMouseDown: move}, children: []},
          {tag: "div", attributes: {className: "group-item-name", text: option.name}, children: []},
          {tag: "i", attributes: {className: "fa fa-edit group-item-edit", onClick: this.openEditOption.bind(this, group.id, option.id)}, children: []},
          {tag: "i", attributes: {className: "fa fa-trash-o group-item-remove", onClick: this.removeOption.bind(this, group.id, option.id), children: []}}
        ]}
      }
    }.bind(this))},
    {tag: "div", attributes: {className: "add-item-wrapper"}, children: [
      {tag: "div", attributes: {className: "input-wrapper"}, children: [
        {tag: "input", attributes: {className: "add-item-input", type: "text"}, children: []}
      ]},
      {tag: "div", attributes: {className: "input-wrapper"}, children: [
        {tag: "input", attributes: {className: "add-item-btn btn", value: "Add", type: "button", onClick: this.addItem.bind(this, group.id)}}
      ]}
    ]}
  ]}

  var html = Utils.buildHTML(struct);
  
  console.log(html)

  if (wrapper !== null){
    wrapper.parentNode.insertBefore(html, wrapper);
    wrapper.parentNode.removeChild(wrapper);
  } else {
    parent.appendChild(html)
  }
}

ItemOptionEditor.prototype.buildOptionGroups = function() {
  for (var i=0; i<this.groups.length; i++) {
    this.buildOptionGroup(this.groups[i])
  }
}