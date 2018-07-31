var UserEditor = function(){
  this.users = [];
  this.user = {diningHalls: []};
  this.diningHalls = [];
  this.filters = {
    page: 0
  }
}

UserEditor.prototype.getLocations = function(){
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/locations",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.diningHalls = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))  
}

UserEditor.prototype.getUsers = function(){
  var params = Object.keys(this.filters).map(function(filter){ return filter + "=" + this.filters[filter] }.bind(this)).join("&")

  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/users?" + params,
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.users = this.users.concat(resp);
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))
}

UserEditor.prototype.getUser = function(user_id){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/users/" + user_id,
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.user = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))
}

UserEditor.prototype.saveUser = function(e){
  e.stopPropagation();
  e.preventDefault();

  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/users/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(this.user),
      success: function(resp) {
        // this.user = resp;
        // res();
        window.location = "/admin/users"
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this)) 
}

UserEditor.prototype.updateUser = function(key, e) {
  if (key === "active" || key === "blocked") {
    this.user[key] = !!parseInt(e.target.value, 10);
  } else {
    this.user[key] = e.target.value;
  }

  this.buildUserForm(this.user);  
  document.querySelector("#" + e.target.id).focus()
}

UserEditor.prototype.toggleLocation = function(loc, e) {
  var existing = this.user.diningHalls.filter(function(l){
    return +l.id === +loc.id
  })[0];

  if (!!existing){
    this.user.diningHalls.splice(this.user.diningHalls.indexOf(existing), 1);
  } else {
    this.user.diningHalls.push(loc);
  }

  this.buildUserForm(this.user);
  document.querySelector("#" + e.target.id).focus()
}

UserEditor.prototype.hasLoc = function(loc) {
  return !!this.user.diningHalls.filter(function(l){ return l.id === loc.id })[0]
}

UserEditor.prototype.buildUserForm = function(user) {
  user = user || {};

  var parent = document.querySelector(".user-form-wrapper"),
      wrapper = document.querySelector(".user-form-wrapper form");

  var types = {
    "on-campus": "Student (On Campus)",
    "off-campus": "Student (Off Campus)",
    "manager": "Manager",
    "admin": "Administrator"
  }

  var struct = {tag: "form", attributes: {className: 'user-form', onSubmit: this.saveUser.bind(this)}, children: [
    {tag: "input", attributes: {type: "hidden", value: user.id || 0}},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "User Type"}},
      {tag: "div", attributes: {className: "options"}, children: ["on-campus", "off-campus", "manager", "admin"].map(function(type){
        return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", checked: type === user.userType, name: "user-type", value: type, id: "user-type-" + type, onChange: this.updateUser.bind(this, "userType")}},
          {tag: "label", attributes: {className: 'btn', text: types[type], "for": "user-type-1"}} 
        ]}
      }.bind(this))}
    ]},
    {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
      {tag: "label", attributes: {text: "HUID"}},
      {tag: "input", attributes: {type: "text", id: "huid", name:"huid", value: user.huid || "", onChange: this.updateUser.bind(this, "huid")}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper first-name"}, children: [
      {tag: "label", attributes: {text: "First Name"}},
      {tag: "input", attributes: {type: "text", id: "firstName", value: user.firstName || "", onChange: this.updateUser.bind(this, "firstName")}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper last-name"}, children: [
      {tag: "label", attributes: {text: "Last Name"}},
      {tag: "input", attributes: {type: "text", id: "lastName", value: user.lastName || "", onChange: this.updateUser.bind(this, "lastName")}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper email"}, children: [
      {tag: "label", attributes: {text: "Email"}},
      {tag: "input", attributes: {type: "text", id: "email", value: user.email || "", onChange: this.updateUser.bind(this, "email")}}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Status"}},
      {tag: "div", attributes: {className: "options"}, children: [true, false].map(function(status){
        var label = status ? "Active" : "Inactive",
            value = +status; 

        return {tag: "div", attributes: {id: "active", className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", checked: status === this.user.active, name: "status", value: value, id: "active-" + value, onChange: this.updateUser.bind(this, "active")}},
          {tag: "label", attributes: {className: 'btn', text: label, "for": "active-" + value}} 
        ]}
      }.bind(this))}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "Block User"}},
      {tag: "div", attributes: {id: "blocked", className: "options"}, children: [true, false].map(function(status){
        var label = status ? "Yes" : "No",
            value = +status; 

        return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", checked: status === this.user.blocked, name: "blocked", value: value, id: "blocked-" + value, onChange: this.updateUser.bind(this, "blocked")}},
          {tag: "label", attributes: {className: 'btn', text: label, "for": "blocked-" + value}} 
        ]}
      }.bind(this))}
    ]},
    {tag: "fieldset", attributes: {}, children: [
      {tag: "legend", attributes: {text: "diningHalls"}},
      {tag: "div", attributes: {className: "options"}, children: this.diningHalls.map(function(loc){        
        return {tag: "div", attributes: {className: "input-wrapper checkbox"}, children: [
          {tag: "input", attributes: {id: "location-" + loc.id, type: "checkbox", checked: this.hasLoc(loc), name: "blocked", value: loc.id, id: "loc-" + loc.id, onChange: this.toggleLocation.bind(this, loc)}},
          {tag: "label", attributes: {className: 'btn', text: loc.name, "for": "loc-" + loc.id}} 
        ]}
      }.bind(this))}
    ]},

    {tag: "div", attributes: {className: "btns"}, children: [
      {tag: "input", attributes: {className: "btn submit confirm", type: "submit"}, children: []},
      {tag: "a", attributes: {className: "btn cancel", href: "/admin/users", text: "Cancel"}, children: []}
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

UserEditor.prototype.buildUsers = function(user) {
  for (var i=0; i<this.users.length; i++) {
    var user = this.users[i],
        parent = document.querySelector(".users"),
        wrapper = document.querySelector(".user-" + user.id);

    var struct = {tag: "li", attributes: {className: "info-list user user-" + user.id}, children: [
      {tag: "div", attributes: {className: "user-info huid", text: user.huid}, children: []},
      {tag: "div", attributes: {className: "user-info name", text: user.lastName + ", " + user.firstName}, children: []},
      {tag: "div", attributes: {className: "user-info userType", text: user.userType}, children: []},
      {tag: "div", attributes: {className: "user-info status", text: user.status ? "Active" : "Inactive"}, children: []},
      {tag: "div", attributes: {className: "action"}, children: [
        {tag: "a", attributes: {href: "/admin/users/" + user.id}, children: [
          {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
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
