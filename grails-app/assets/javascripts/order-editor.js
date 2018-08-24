var OrderEditor = function(){
  this.count = 0
  this.orders = [];
  this.reports = [];
  this.history = [];
  this.order = {diningHalls: []};
  this.sort = {
    sortField: null,
    sortOrder: null
  };
  this.filters = {
    page: 0
  };
  this.reportFilters = {
    page: 0
  };
  this.historyFilters = {
    page: 0
  };
}

OrderEditor.prototype.search = function(){
  return new Promise(function(res, rej){
    

    $.ajax({
      url: "/admin/api/orders/search?" + $.param(this.filters) + "&" + $.param(this.sort),
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.count = resp.count
        this.orders = resp.results
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
      url: "/admin/api/orders/report?" + $.param(this.reportFilters) + "&" + $.param(this.sort),
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.count = resp.count[0]
        this.reports = resp.results
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))  
}

OrderEditor.prototype.getHistory = function(){
  return new Promise(function(res, rej){
    $.ajax({
      url: "/myhuds/orders/history?" + $.param(this.historyFilters) + "&" + $.param(this.sort),
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        this.count = resp.count
        this.history = resp.results
        res();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))  
}

OrderEditor.prototype.markNotPickedUp = function(id) {
  return new Promise(function(res, rej){
    $.ajax({
      url: "/admin/api/pickup/" + id + "/report",
      type: "GET",
      contentType: "application/json",
      success: function(resp) {
        var order = this.orders.filter(function(o){
          return o.id === resp.id
        })[0];

        if (!!order){
          order.pickedUp = false;  
        }

        this.buildOrders();
      }.bind(this),
      error: function(err) {
        console.warn(err)
        rej();
      }.bind(this)
    })
  }.bind(this))
}

OrderEditor.prototype.updateSort = function(key, callback) {
  if (this.sort.key === key){
    this.sort.sortOrder = (this.sort.sortOrder === "asc" ? "desc" : "asc");  
  } else {
    this.sort.sortField = key;
    this.sort.sortOrder = (this.sort.sortOrder === "asc" ? "desc" : "asc");
  }

  return callback()
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

OrderEditor.prototype.previousPage = function() {
  if (this.filters.page > 0){
    this.filters.page -= 1;
    this.search().then(function(){
      document.querySelector(".admin-list.orders").innerHTML = "";
      this.buildOrders();
      if (this.filters.page === 0){
        document.querySelector(".btn.prev").style.display = "none";
      }
    }.bind(this))
  }
}

OrderEditor.prototype.nextPage = function() {
  // if (this.filters.page){
    this.filters.page += 1;
    this.search().then(function(){
      document.querySelector(".admin-list.orders").innerHTML = "";
      this.buildOrders();
      if (this.filters.page > 0){
        document.querySelector(".btn.prev").style.display = "inline-block";
      } 
    }.bind(this))
  // }
}

OrderEditor.prototype.previousReportPage = function() {
  if (this.reportFilters.page > 0){
    this.reportFilters.page -= 1;
    this.getReport().then(function(){
      document.querySelector(".admin-list.reports").innerHTML = "";
      this.buildReports();
      if (this.reportFilters.page === 0){
        document.querySelector(".btn.prev").style.display = "none";
      }
    }.bind(this))
  }
}

OrderEditor.prototype.nextReportPage = function() {
  // if (this.filters.page){
    this.reportFilters.page += 1;
    this.getReport().then(function(){
      document.querySelector(".admin-list.reports").innerHTML = "";
      this.buildReports();
      if (this.reportFilters.page > 0){
        document.querySelector(".btn.prev").style.display = "inline-block";
      } 
    }.bind(this))
  // }
}

OrderEditor.prototype.previousHistoryPage = function() {
  if (this.historyFilters.page > 0){
    this.historyFilters.page -= 1;
    this.getHistory().then(function(){
      document.querySelector(".admin-list.history").innerHTML = "";
      this.buildHistory();
      if (this.historyFilters.page === 0){
        document.querySelector(".btn.prev").style.display = "none";
      }
    }.bind(this))
  }
}

OrderEditor.prototype.nextHistoryPage = function() {
  // if (this.filters.page){
    this.historyFilters.page += 1;
    this.getHistory().then(function(){
      document.querySelector(".admin-list.history").innerHTML = "";
      this.buildHistory();
      if (this.historyFilters.page > 0){
        document.querySelector(".btn.prev").style.display = "inline-block";
      } 
    }.bind(this))
  // }
}

OrderEditor.prototype.filter = function(key, value, e) {
  if (value == ""){
    delete this.filters[key]
  } else {
    if (key === "start_date" || key === "end_date") {
      if (!moment(value, 'YYYY-MM-DD', true)) {
        return
      }

      if (moment(value, "YYYY-MM-DD").isAfter(moment())){
        e.target.value = moment().format("YYYY-MM-DD");
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
  document.querySelector(".count").innerHTML = this.count + " results matched your query"
}

OrderEditor.prototype.buildEmptyReports = function(){
  document.querySelector(".reports").innerHTML = ""
}

OrderEditor.prototype.isComplete = function(order){
  for (var i=0; i<(order.orderPickups || []).length; i++){
    if (moment(order.orderPickups[i].pickupDate).isAfter(moment())){
      return false
    }
  }

  return true
}

OrderEditor.prototype.buildOrders = function() {
  for (var i=0; i<this.orders.length; i++) {
    var pickup = this.orders[i],
        order = pickup.orders,
        parent = document.querySelector(".orders"),
        wrapper = document.querySelector(".order-" + pickup.id);

    var struct = {tag: "li", attributes: {className: "info-list order order-" + pickup.id}, children: [
      {tag: "div", attributes: {className: "order-info date", text: moment(pickup.pickupDate).format("MM/DD/YYYY") + moment(pickup.pickupTime).format(" h:mm a") }},
      {tag: "div", attributes: {className: "order-info location ", text: !!order.diningHall ? order.diningHall.name : ""}},
      {tag: "div", attributes: {className: "order-info meal", text: !!order.menu ? order.menu.meal.name : ""}},
      {tag: "div", attributes: {className: "order-info user"}, children: [
        {tag: "div", attributes: {text: order.user.huid + " /"}},
        // {tag: "div", attributes: {text: " /"}},
        {tag: "div", attributes: {text: order.user.lastName + ", " + order.user.firstName }}
      ]},
      {tag: "div", attributes: {className: "order-info user"}, children: (order.menuSelections || []).map(function(s){ 
        return {tag: "div", attributes: {text: s.menuItem.name}}
      })},
      {tag: "div", attributes: {className: "action"}, children: [
        {tag: "div", attributes: {text: !!pickup && pickup.pickedUp ? "Picked Up" : "Not Picked Up" }},
        {tag: "input", attributes: {type: "button", className: "btn", value: "Change", onClick: this.markNotPickedUp.bind(this, pickup.id) }, condition: function(p){ return p.pickedUp }.bind(this, pickup)}
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

  document.querySelector(".count").innerHTML = this.count + " results matched your query"
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

  document.querySelector(".count").innerHTML = this.count + " results matched your query"
}

OrderEditor.prototype.buildHistory = function() {
  for (var i=0; i<this.history.length; i++) {
    var order = this.history[i],
        parent = document.querySelector(".history"),
        wrapper = document.querySelector(".order-" + order.id);

    var pickupDates = order.orderPickups.sort(function(a, b){
      return moment.utc(a.pickupDate).diff(moment.utc(b.pickupDate))
    })  

    var struct = {tag: "li", attributes: {className: "info-list order order-" + order.id}, children: [
      {tag: "div", attributes: {className: "order-info date", text: moment(pickupDates[0].pickupDate).format("M/D/YYYY") + " " + moment.utc(pickupDates[0].pickupTime).format("h:mm a") }},
      {tag: "div", attributes: {className: "order-info location ", text: order.diningHall.name }},
      {tag: "div", attributes: {className: "order-info meal", text: order.menu.meal.name }},
      {tag: "div", attributes: {className: "order-info selections"}, children: order.menuSelections.map(function(selection){
        return {tag: "div", attributes: {className: "selection", text: selection.menuItem.name }}
      })},
      {tag: "div", attributes: {className: "order-item repeated", text: pickupDates.length > 1 ? "Repeat Until " + moment(pickupDates[pickupDates.length - 1].pickupDate).format("M/D/YYYY") : "" }},
      (order.canceled  
        ? {tag: "div", attributes: {className: "order-info status", text: "Canceled on " + moment(order.canceledOn).format("dddd, MMMM DD")}} 
        : (this.isComplete(order) 
          ? {tag: "div", attributes: {className: "order-info status", text: "Picked Up"}}
          : {tag: "div", attributes: {className: "order-info status action"}, children: [
              {tag: "a", attributes: {className: "btn", href: "/myhuds/orders/" + order.menu.meal.name.toLowerCase() + "/create/" + order.id, text: "Edit" }},
              {tag: "a", attributes: {className: "btn", href: "/myhuds/orders/" + order.id + "/cancel", text: "Cancel"}}
            ]}
        )
      )
    ]}

    var html = Utils.buildHTML(struct);

    if (wrapper !== null){
      wrapper.parentNode.insertBefore(html, wrapper);
      wrapper.parentNode.removeChild(wrapper);
    } else {
      parent.appendChild(html)
    }
  }

  document.querySelector(".count").innerHTML = this.count + " results matched your query"
}
