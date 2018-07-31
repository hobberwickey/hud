var LocationEditor = function(){
  this.locations = [];
  this.location = {menus: []};
  this.menus = [];
}

LocationEditor.prototype.refreshMenus = function() {
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/menus",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.menus = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
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

LocationEditor.prototype.getLocation = function(location_id){
  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/locations/" + location_id,
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.location = resp;
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

LocationEditor.prototype.saveLocation = function(e){
  e.stopPropagation();
  e.preventDefault();

  return new Promise(function(res, rej) {
    $.ajax({
      url: "/admin/api/locations/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(this.location),
      success: function(resp) {
        window.location = "/admin/locations"
        // if (window.location.pathname.split("/")[3] === "new" && !!resp.id){
        //   window.location.assign("/admin/locations/" + resp.id);
        // } else {
        //   this.location = resp;
        //   this.buildLocationForm(this.location);
        // }

        // res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej(err)
      }.bind(this)
    })
  }.bind(this))
}

LocationEditor.prototype.updateLocation = function(key, e){
  console.log(key, e.target.value)

  if (key === "openingDate" || key === "closingDate") {
    this.location[key] = moment(e.target.value, "YYYY-MM-DD")
  } else if (key === "status") {
    this.location[key] = !!e.target.value
  } else {
    this.location[key] = e.target.value;
  }
}

LocationEditor.prototype.hasMenu = function(menu){
  var menus = this.location.menus || [];

  for (var i=0; i<menus.length; i++){
    if (menus[i].id === menu.id) return true
  }

  return false;
}

LocationEditor.prototype.getSimilarMenu = function(menu){
  var menus = this.location.menus || [];

  for (var i=0; i<menus.length; i++){
    if (!!menu.meal && !!menus[i].meal && menu.meal.name === menus[i].meal.name){
      return menus[i];
    }
  }

  return null;
}

LocationEditor.prototype.toggleMenu = function(menu){
  var similar = this.getSimilarMenu(menu);

  if (similar !== null){
    console.log(this.location.menus.indexOf(similar))
    this.location.menus.splice(this.location.menus.indexOf(similar), 1);
  }

  if (!!similar && similar.id === menu.id){
    // Do nothing
  } else {
    this.location.menus.push(menu)
  }

  this.buildLocationForm(this.location);
}

LocationEditor.prototype.buildLocationForm = function(location) {
  location = location || {};

  var parent = document.querySelector(".location-form-wrapper"),
      wrapper = document.querySelector(".location-form-wrapper form");

  var struct = {tag: "form", attributes: {className: 'location-form', onSubmit: this.saveLocation.bind(this)}, children: [
    {tag: "input", attributes: {type: "hidden", name: "id", value: location.id || 0}},

    {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
      {tag: "label", attributes: {text: "Location Name"}},
      {tag: "input", attributes: {type: "text", name:"name", value: location.name || "", onChange: this.updateLocation.bind(this, "name")}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper opening-date"}, children: [
      {tag: "label", attributes: {text: "Semester Start"}},
      {tag: "input", attributes: {type: "date", name: "openingDate", value: !!location.openingDate ? moment(location.openingDate).format("YYYY-MM-DD") : "", onChange: this.updateLocation.bind(this, "openingDate")}}
    ]},
    {tag: "div", attributes: {className: "input-wrapper closing-date"}, children: [
      {tag: "label", attributes: {text: "Semester End"}},
      {tag: "input", attributes: {type: "date", type: "date", name: "closingDate", value: !!location.closingDate ? moment(location.closingDate).format("YYYY-MM-DD") : "", onChange: this.updateLocation.bind(this, "closingDate")}}
    ]},
    {tag: "fieldset", attributes: {className: "status"}, children: [
      {tag: "legend", attributes: {text: "Status"}},
      {tag: "div", attributes: {className: "options"}, children: [true, false].map(function(val){
        var name = val ? "Active" : "Inactive",
            value = +val;

        return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
          {tag: "input", attributes: {type: "radio", checked: (location.status === val), name: "status", value: value, id: "active-" + value, onChange: this.updateLocation.bind(this, "status")}},
          {tag: "label", attributes: {className: 'btn', text: name, "for": "status-" + value}} 
        ]}
      }.bind(this))}
    ]},

    {tag: "fieldset", attributes: {className: "breakfast"}, children: [
      {tag: "legend", attributes: {text: "Breakfast"}},
      {tag: "div", attributes: {className: "options"}, children: this.menus.filter(function(m){ return !!m.meal && m.meal.name === "Breakfast"}).map(function(menu){

        return {tag: "div", attributes: {className: "input-wrapper checkbox"}, children: [
          {tag: "input", attributes: {type: "checkbox", checked: this.hasMenu(menu), name: "breakfast", value: menu.id, id: "breakfast-" + menu.id, onChange: this.toggleMenu.bind(this, menu)}},
          {tag: "label", attributes: {className: 'btn', text: menu.name, "for": "menu-" + menu.id}} 
        ]}
      }.bind(this))}
    ]},

    {tag: "fieldset", attributes: {className: "breakfast"}, children: [
      {tag: "legend", attributes: {text: "Lunch"}},
      {tag: "div", attributes: {className: "options"}, children: this.menus.filter(function(m){ return !!m.meal && m.meal.name === "Lunch"}).map(function(menu){

        return {tag: "div", attributes: {className: "input-wrapper checkbox"}, children: [
          {tag: "input", attributes: {type: "checkbox", checked: this.hasMenu(menu), name: "lunch", value: menu.id, id: "lunch-" + menu.id, onChange: this.toggleMenu.bind(this, menu)}},
          {tag: "label", attributes: {className: 'btn', text: menu.name, "for": "menu-" + menu.id}} 
        ]}
      }.bind(this))}
    ]},

    {tag: "fieldset", attributes: {className: "breakfast"}, children: [
      {tag: "legend", attributes: {text: "Dinner"}},
      {tag: "div", attributes: {className: "options"}, children: this.menus.filter(function(m){ return !!m.meal && m.meal.name === "Dinner"}).map(function(menu){

        return {tag: "div", attributes: {className: "input-wrapper checkbox"}, children: [
          {tag: "input", attributes: {type: "checkbox", checked: this.hasMenu(menu), name: "dinner", value: menu.id, id: "dinner-" + menu.id, onChange: this.toggleMenu.bind(this, menu)}},
          {tag: "label", attributes: {className: 'btn', text: menu.name, "for": "menu-" + menu.id}} 
        ]}
      }.bind(this))}
    ]},

    {tag: "div", attributes: {className: "btns"}, children: [
      {tag: "input", attributes: {className: "btn submit confirm", type: "submit"}, children: []},
      {tag: "a", attributes: {className: "btn cancel", href: "/admin/locations", text: "Cancel"}, children: []}
    ]}

    //Dining Halls
  ]}

  var html = Utils.buildHTML(struct);
  
  if (wrapper !== null){
    wrapper.parentNode.insertBefore(html, wrapper);
    wrapper.parentNode.removeChild(wrapper);
  } else {
    parent.appendChild(html)
  }
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
      {tag: "a", attributes: {href: "/admin/locations/" + location.id}, children: [
        {tag: "i", attributes: {className: "fa fa-edit"}, children: []}
      ]}
    ]},
    {tag: "div", attributes: {className: "action"}, children: [
      {tag: "a", attributes: {href: "/admin/locations/" + location.id + "/delete"}, children: [
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
