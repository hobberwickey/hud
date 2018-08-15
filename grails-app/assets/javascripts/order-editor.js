var OrderEditor = function(){
  this.orders = [];
  this.reports = [];
  this.order = {diningHalls: []};
  this.filters = {
    page: 0
  }
  this.reportFilters = {
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

OrderEditor.prototype.getReport = function(){
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/orders/report?" + $.param(this.reportFilters),
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.reports = resp
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))  
}

OrderEditor.prototype.filterReports = function(key, value){
  if (value == ""){
    delete this.reportFilters[key]
  } else {
    if (key === "start_date" || key === "end_date") {
      if (!moment(value, 'YYYY-MM-DD', true)) {
        return
      }
    }

    this.reportFilters[key] = value
  }

  this.updateReports();
}

OrderEditor.prototype.updateReports = function() {
  this.getReport().then(function(){
    if (this.reports.length > 0){
      document.querySelector(".reports").innerHTML = "";
      this.buildReports()
    } else {
      this.buildEmptyReports()
    }
  }.bind(this))
}

OrderEditor.prototype.filter = function(key, value) {
  if (value == ""){
    delete this.filters[key]
  } else {
    if (key === "start_date" || key === "end_date") {
      if (!moment(value, 'YYYY-MM-DD', true)) {
        return
      }
    }

    this.filters[key] = value
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

OrderEditor.prototype.buildEmptyResults = function(){
  document.querySelector(".orders").innerHTML = ""
}

OrderEditor.prototype.buildEmptyReports = function(){
  document.querySelector(".reports").innerHTML = ""
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

OrderEditor.prototype.buildReports = function() {
  for (var i=0; i<this.reports.length; i++) {
    var report = this.reports[i],
        parent = document.querySelector(".reports"),
        wrapper = document.querySelector(".report-" + report[0] + "-" + report[3]);

    var struct = {tag: "li", attributes: {className: "info-list report report-" + report[0] + "-" + report[3]}, children: [
      {tag: "div", attributes: {className: "order-info name", text: report[1] }},
      {tag: "div", attributes: {className: "order-info popularity ", text: report[2] }},
      {tag: "div", attributes: {className: "order-info location", text: report[4] }},
      {tag: "div", attributes: {className: "order-info meal", text: report[5] }}
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
