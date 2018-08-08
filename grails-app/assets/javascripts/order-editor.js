var OrderEditor = function(){
  this.orders = [];
  this.order = {diningHalls: []};
  this.filters = {
    page: 0
  }
}

OrderEditor.prototype.search = function(){
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/orders/search?" + $.param(this.filters),
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.orders = resp
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))  
}

OrderEditor.prototype.filterUser = function(value) {
  if (value == ""){
    delete this.filters.user
  } else {
    this.filters.user = value
  }

  this.updateOrders();
}

OrderEditor.prototype.filterLocation = function(value) {
  if (value == ""){
    delete this.filters.location
  } else {
    this.filters.location = value
  }

  this.updateOrders();
}

OrderEditor.prototype.filterMeal = function(value) {
  if (value == ""){
    delete this.filters.meal
  } else {
    this.filters.meal = value
  }

  this.updateOrders();
}

OrderEditor.prototype.updateOrders = function(){
  this.search().then(function(){
    if (this.orders.length > 0){
      document.querySelector(".orders").innerHTML = "";
      this.buildOrders()
    } else {
      this.buildEmptyResults()
    }
  }.bind(this))
}

// OrderEditor.prototype.saveOrder = function(e){
//   e.stopPropagation();
//   e.preventDefault();

//   return new Promise(function(res, rej) {
//     $.ajax({
//       url: "/admin/api/users/save",
//       type: "POST",
//       contentType: "application/json",
//       data: JSON.stringify(this.user),
//       success: function(resp) {
//         // this.user = resp;
//         // res();
//         window.location = "/admin/users"
//       }.bind(this),
//       error: function(err) {
//         console.warn(err)
//         rej();
//       }.bind(this)
//     })
//   }.bind(this)) 
// }

// OrderEditor.prototype.updateOrder = function(key, e) {
//   if (key === "active" || key === "blocked") {
//     this.user[key] = !!parseInt(e.target.value, 10);
//   } else {
//     this.user[key] = e.target.value;
//   }

//   this.buildUserForm(this.user);  
//   document.querySelector("#" + e.target.id).focus()
// }


// OrderEditor.prototype.buildOrderForm = function(user) {
//   user = user || {};

//   var parent = document.querySelector(".user-form-wrapper"),
//       wrapper = document.querySelector(".user-form-wrapper form");

//   var types = {
//     "on-campus": "Student (On Campus)",
//     "off-campus": "Student (Off Campus)",
//     "manager": "Manager",
//     "admin": "Administrator"
//   }

//   var struct = {tag: "form", attributes: {className: 'user-form', onSubmit: this.saveUser.bind(this)}, children: [
//     {tag: "input", attributes: {type: "hidden", value: user.id || 0}},
//     {tag: "fieldset", attributes: {}, children: [
//       {tag: "legend", attributes: {text: "User Type"}},
//       {tag: "div", attributes: {className: "options"}, children: ["on-campus", "off-campus", "manager", "admin"].map(function(type){
//         return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
//           {tag: "input", attributes: {type: "radio", checked: type === user.userType, name: "user-type", value: type, id: "user-type-" + type, onChange: this.updateUser.bind(this, "userType")}},
//           {tag: "label", attributes: {className: 'btn', text: types[type], "for": "user-type-1"}} 
//         ]}
//       }.bind(this))}
//     ]},
//     {tag: "div", attributes: {className: "input-wrapper huid"}, children: [
//       {tag: "label", attributes: {text: "HUID"}},
//       {tag: "input", attributes: {type: "text", id: "huid", name:"huid", value: user.huid || "", onChange: this.updateUser.bind(this, "huid")}}
//     ]},
//     {tag: "div", attributes: {className: "input-wrapper first-name"}, children: [
//       {tag: "label", attributes: {text: "First Name"}},
//       {tag: "input", attributes: {type: "text", id: "firstName", value: user.firstName || "", onChange: this.updateUser.bind(this, "firstName")}}
//     ]},
//     {tag: "div", attributes: {className: "input-wrapper last-name"}, children: [
//       {tag: "label", attributes: {text: "Last Name"}},
//       {tag: "input", attributes: {type: "text", id: "lastName", value: user.lastName || "", onChange: this.updateUser.bind(this, "lastName")}}
//     ]},
//     {tag: "div", attributes: {className: "input-wrapper email"}, children: [
//       {tag: "label", attributes: {text: "Email"}},
//       {tag: "input", attributes: {type: "text", id: "email", value: user.email || "", onChange: this.updateUser.bind(this, "email")}}
//     ]},
//     {tag: "fieldset", attributes: {}, children: [
//       {tag: "legend", attributes: {text: "Status"}},
//       {tag: "div", attributes: {className: "options"}, children: [true, false].map(function(status){
//         var label = status ? "Active" : "Inactive",
//             value = +status; 

//         return {tag: "div", attributes: {id: "active", className: "input-wrapper radio"}, children: [
//           {tag: "input", attributes: {type: "radio", checked: status === this.user.active, name: "status", value: value, id: "active-" + value, onChange: this.updateUser.bind(this, "active")}},
//           {tag: "label", attributes: {className: 'btn', text: label, "for": "active-" + value}} 
//         ]}
//       }.bind(this))}
//     ]},
//     {tag: "fieldset", attributes: {}, children: [
//       {tag: "legend", attributes: {text: "Block User"}},
//       {tag: "div", attributes: {id: "blocked", className: "options"}, children: [true, false].map(function(status){
//         var label = status ? "Yes" : "No",
//             value = +status; 

//         return {tag: "div", attributes: {className: "input-wrapper radio"}, children: [
//           {tag: "input", attributes: {type: "radio", checked: status === this.user.blocked, name: "blocked", value: value, id: "blocked-" + value, onChange: this.updateUser.bind(this, "blocked")}},
//           {tag: "label", attributes: {className: 'btn', text: label, "for": "blocked-" + value}} 
//         ]}
//       }.bind(this))}
//     ]},
//     {tag: "fieldset", attributes: {}, children: [
//       {tag: "legend", attributes: {text: "diningHalls"}},
//       {tag: "div", attributes: {className: "options"}, children: this.diningHalls.map(function(loc){        
//         return {tag: "div", attributes: {className: "input-wrapper checkbox"}, children: [
//           {tag: "input", attributes: {id: "location-" + loc.id, type: "checkbox", checked: this.hasLoc(loc), name: "blocked", value: loc.id, id: "loc-" + loc.id, onChange: this.toggleLocation.bind(this, loc)}},
//           {tag: "label", attributes: {className: 'btn', text: loc.name, "for": "loc-" + loc.id}} 
//         ]}
//       }.bind(this))}
//     ]},

//     {tag: "div", attributes: {className: "btns"}, children: [
//       {tag: "input", attributes: {className: "btn submit confirm", type: "submit"}, children: []},
//       {tag: "a", attributes: {className: "btn cancel", href: "/admin/users", text: "Cancel"}, children: []}
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

OrderEditor.prototype.buildEmptyResults = function(){
  document.querySelector(".orders").innerHTML = ""
}

OrderEditor.prototype.buildOrders = function() {
  for (var i=0; i<this.orders.length; i++) {
    var order = this.orders[i],
        parent = document.querySelector(".orders"),
        wrapper = document.querySelector(".order-" + order.id);

    var struct = {tag: "li", attributes: {className: "info-list order order-" + order.id}, children: [
      {tag: "div", attributes: {className: "order-info date", text: !!order.orderPickups[0] ? moment(order.orderPickups[0].pickupDate).format("MM/DD/YYYY") : "" }},
      {tag: "div", attributes: {className: "order-info location ", text: !!order.diningHall ? order.diningHall.name : ""}},
      {tag: "div", attributes: {className: "order-info meal", text: !!order.menu ? order.menu.meal.name : ""}},
      {tag: "div", attributes: {className: "order-info user", text: !!order.user ? order.user.huid + "/" + order.user.lastName + ", " + order.user.firstName : ""}, children: []},
      {tag: "div", attributes: {className: "order-info user", text: (order.menuSelections || []).map(function(s){ return s.menuItem.name }).join(", ")}},
      {tag: "div", attributes: {className: "action"}, children: [
        {tag: "div", attributes: {text: !!order.orderPickups[0] && order.orderPickups[0].pickedUp ? "Picked Up" : "Picked Up" }},
        {tag: "input", attributes: {type: "button", className: "btn", value: "Change"}}
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
