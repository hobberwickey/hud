var LocationEditor = function(){
  this.locations = [];
  // this.refreshLocations();
}


LocationEditor.prototype.refreshLocations = function(){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/locations",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.locations = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

LocationEditor.prototype.saveLocation = function(location){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/locations/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(location),
      success: function(resp) {
        this.locations.push(resp);
        // this.buildLocationForm(resp);
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

LocationEditor.prototype.submitForm = function(e) {
  e.stopPropagation();
  e.preventDefault();

  var values = {}
  new FormData(document.querySelector(".location-form")).forEach(function(value, key) {
    if (key === "id") { 
      values[key] = parseInt(value, 10);
    } else if (key === "status") {
      values[key] = !!parseInt(value, 10);
    } else if (key === "openingDate" || key === "closingDate"){
      values[key] = moment(value, "YYYY-MM-DD");
    } else {
      values[key] = value;
    }
  });

  this.saveLocation(values);
}

LocationEditor.prototype.buildLocationForm = function(location) {
  location = location || {};

  var struct = {tag: "form", attributes: {className: 'location-form', onSubmit: this.submitForm.bind(this)}, children: [
    {tag: "input", attributes: {type: "hidden", name: "id", value: location.id || 0}},

    {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
      {tag: "label", attributes: {text: "Location Name"}},
      {tag: "input", attributes: {type: "text", name:"name", value: location.name || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper opening-date"}, children: [
      {tag: "label", attributes: {text: "Semester Start"}},
      {tag: "input", attributes: {type: "text", type: "date", name: "openingDate", value: location.openingDate || ""}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper closing-date"}, children: [
      {tag: "label", attributes: {text: "Semester End"}},
      {tag: "input", attributes: {type: "text", type: "date", name: "closingDate", value: location.closingDate || ""}}
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

    {tag: "div", attributes: {className: "btns"}, children: [
      {tag: "input", attributes: {className: "btn submit confirm", type: "submit"}, children: []},
      {tag: "a", attributes: {className: "btn cancel", href: "/admin/locations", text: "Cancel"}, children: []}
    ]}

    //Dining Halls
  ]}

  return Utils.buildHTML(struct);
}

LocationEditor.prototype.buildLocation = function(location) {
  var parent = document.querySelector(".locations"),
      wrapper = document.querySelector(".location-" + location.id);

  var struct = {tag: "li", attributes: {className: "info-list location-" + location.id}, children: [
    {tag: "div", attributes: {className: "info name", text: location.name}, children: []},
    {tag: "div", attributes: {className: "info openingDate", text: moment(location.openingDate).format("MM/DD/YYYY")}, children: []},
    {tag: "div", attributes: {className: "info closingDate", text: moment(location.closingDate).format("MM/DD/YYYY")}, children: []},
    {tag: "div", attributes: {className: "info menu", text: (location.menus || []).map(function(m){ return m.name }).join(", ") || "Edit to Add Menus"}, children: []},
    {tag: "div", attributes: {className: "info status", text: location.status ? "Active" : "Inactive"}, children: []},
    {tag: "div", attributes: {className: "action"}, children: [
      {tag: "a", attributes: {href: "/admin/users/" + location.id}, children: [
        {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
      ]}
    ]},
    {tag: "div", attributes: {className: "action"}, children: [
      {tag: "a", attributes: {href: "/admin/users/" + location.id + "/delete"}, children: [
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

LocationEditor.prototype.buildLocations = function() {
  for (var i=0; i<this.locations.length; i++) {
    this.buildLocation(this.locations[i])
  }
}