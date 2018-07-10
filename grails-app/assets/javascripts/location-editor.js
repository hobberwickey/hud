var LocationEditor = function(){
  this.locations = [];
  this.refreshLocations();
}


LocationEditor.prototype.refreshLocations = function(){
  $.ajax({
    url: "/admin/api/locations",
    type: "GET",
    contentType: "application/json",
    success: function(resp) {
      this.locations = resp
    }.bind(this),
    error: function(err) {
      console.warn(err)
    }.bind(this)
  })
}

LocationEditor.prototype.buildLocationForm = function(location) {
  location = location || {};

  var struct = {tag: "form", attributes: {className: 'location-form'}, children: [
    {tag: "input", attributes: {type: "hidden", value: location.id || 0}},

    {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
      {tag: "label", attributes: {text: "Location Name"}},
      {tag: "input", attributes: {type: "text", name:"name", value: location.name || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper opening-date"}, children: [
      {tag: "label", attributes: {text: "Semester Start"}},
      {tag: "input", attributes: {type: "text", type: "date", value: location.openingDate || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper closing-date"}, children: [
      {tag: "label", attributes: {text: "Semester End"}},
      {tag: "input", attributes: {type: "text", type: "date", value: location.closingDate || ""}}
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
    ]}

    //Dining Halls
  ]}

  return Utils.buildHTML(struct);
}

LocationEditor.prototype.buildLocation = function(user) {
  // var parent = document.querySelector(".users"),
  //     wrapper = document.querySelector(".user-" + user.id);

  // var struct = {tag: "li", attributes: {className: "user user-" + user.id}, children: [
  //   {tag: "div", attributes: {className: "user-info huid", text: user.huid}, children: []},
  //   {tag: "div", attributes: {className: "user-info name", text: user.lastName + ", " + user.firstName}, children: []},
  //   {tag: "div", attributes: {className: "user-info userType", text: user.userType}, children: []},
  //   {tag: "div", attributes: {className: "user-info status", text: user.status ? "Active" : "Inactive"}, children: []},
  //   {tag: "div", attributes: {className: "user-action"}, children: [
  //     {tag: "a", attributes: {href: "/admin/users/" + user.id}, children: [
  //       {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
  //     ]}
  //   ]},
  //   {tag: "div", attributes: {className: "user-action"}, children: [
  //     {tag: "a", attributes: {href: "/admin/users/" + user.id + "/delete"}, children: [
  //       {tag: "i", attributes: {className: "fa fa-trash-o"}, children: []}
  //     ]}
  //   ]}
  // ]}
}

LocationEditor.prototype.buildLocations = function() {
  
}