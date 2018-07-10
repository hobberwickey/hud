var UserEditor = function(){
  this.users = [];
  this.refreshUsers();
}


UserEditor.prototype.refreshUsers = function(){
  $.ajax({
    url: "/admin/api/users",
    type: "GET",
    contentType: "application/json",
    success: function(resp) {
      this.users = resp
      // this.buildUsers();
    }.bind(this),
    error: function(err) {
      console.warn(err)
    }.bind(this)
  })
}

UserEditor.prototype.buildUserForm = function(user) {
  user = user || {};

  var struct = {tag: "form", attributes: {className: 'user-form'}, children: [
    {tag: "input", attributes: {type: "hidden", value: user.id || 0}},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "User Type"}},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "user-type", value: "student-off-campus", id: "user-type-1"}},
        {tag: "label", attributes: {className: 'btn', text: "Student (Off Campus", "for": "user-type-1"}} 
      ]},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "user-type", value: "manager", id: "user-type-2"}},
        {tag: "label", attributes: {className: 'btn', text: "Manager", "for": "user-type-1"}} 
      ]},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "user-type", value: "admin", id: "user-type-3"}},
        {tag: "label", attributes: {className: 'btn', text: "Administrator", "for": "user-type-1"}} 
      ]}
    ]},
    {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
      {tag: "label", attributes: {text: "HUID"}},
      {tag: "input", attributes: {type: "text", name:"huid", value: user.huid || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper first-name"}, children: [
      {tag: "label", attributes: {text: "First Name"}},
      {tag: "input", attributes: {type: "text", value: user.firstName || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper last-name"}, children: [
      {tag: "label", attributes: {text: "Last Name"}},
      {tag: "input", attributes: {type: "text", value: user.lastName || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper email"}, children: [
      {tag: "label", attributes: {text: "Email"}},
      {tag: "input", attributes: {type: "text", value: user.email || ""}}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Status"}},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "status", value: "1", id: "status-1"}},
        {tag: "label", attributes: {className: 'btn', text: "Active", "for": "status-1"}} 
      ]},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "status", value: "0", id: "user-type-2"}},
        {tag: "label", attributes: {className: 'btn', text: "Inactive", "for": "status-2"}} 
      ]},
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Block User"}},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "blocked", value: "1", id: "blocked-1"}},
        {tag: "label", attributes: {className: 'btn', text: "Yes", "for": "blocked-1"}} 
      ]},
      {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
        {tag: "input", attributes: {type: "radio", name: "blocked", value: "0", id: "blocked-2"}},
        {tag: "label", attributes: {className: 'btn', text: "No", "for": "blocked-2"}} 
      ]}
    ]}
  ]}

  return Utils.buildHTML(struct);
}

UserEditor.prototype.buildUser = function(user) {
  var parent = document.querySelector(".users"),
      wrapper = document.querySelector(".user-" + user.id);

  var struct = {tag: "li", attributes: {className: "user user-" + user.id}, children: [
    {tag: "div", attributes: {className: "user-info huid", text: user.huid}, children: []},
    {tag: "div", attributes: {className: "user-info name", text: user.lastName + ", " + user.firstName}, children: []},
    {tag: "div", attributes: {className: "user-info userType", text: user.userType}, children: []},
    {tag: "div", attributes: {className: "user-info status", text: user.status ? "Active" : "Inactive"}, children: []},
    {tag: "div", attributes: {className: "user-action"}, children: [
      {tag: "a", attributes: {href: "/admin/users/" + user.id}, children: [
        {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
      ]}
    ]},
    {tag: "div", attributes: {className: "user-action"}, children: [
      {tag: "a", attributes: {href: "/admin/users/" + user.id + "/delete"}, children: [
        {tag: "i", attributes: {className: "fa fa-trash-o"}, children: []}
      ]}
    ]}
  ]}
}

UserEditor.prototype.buildUsers = function() {
  
}

// ItemEditor.prototype.buildMenu = function(menu) {
//   var items = (menu.ordering || "").split(",").map(function(id){ return this.items.filter(function(item){ return item.id === parseInt(id, 10) })[0] }.bind(this)).filter(function(item){ return !!item }) || [],
//       parent = document.querySelector(".menus"),
//       wrapper = document.querySelector(".menu-" + menu.id);

//   var struct = {tag: "li", attributes: {className: "menu menu-" + menu.id }, children: [
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
//   for (var i=0; i<this.menus.length; i++){
//     this.buildMenu(this.menus[i]);
//   } 
// }
