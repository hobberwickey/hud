//# sourceMappingURL=order-editor.js.map
var OrderEditor=function(){this.orders=[];this.reports=[];this.history=[];this.order={diningHalls:[]};this.filters={page:0};this.reportFilters={page:0};this.historyFilters={page:0}};OrderEditor.prototype.search=function(){return new Promise(function(b,a){$.ajax({url:"/admin/api/orders/search?"+$.param(this.filters),type:"GET",contentType:"application/json",success:function(a){this.orders=a;b()}.bind(this),error:function(b){console.warn(b);a()}.bind(this)})}.bind(this))};
OrderEditor.prototype.getReport=function(){return new Promise(function(b,a){$.ajax({url:"/admin/api/orders/report?"+$.param(this.reportFilters),type:"GET",contentType:"application/json",success:function(a){this.reports=a;b()}.bind(this),error:function(b){console.warn(b);a()}.bind(this)})}.bind(this))};
OrderEditor.prototype.getHistory=function(){return new Promise(function(b,a){$.ajax({url:"/myhuds/orders/history?"+$.param(this.historyFilters),type:"GET",contentType:"application/json",success:function(a){this.history=a;b()}.bind(this),error:function(b){console.warn(b);a()}.bind(this)})}.bind(this))};
OrderEditor.prototype.markNotPickedUp=function(b){return new Promise(function(a,c){$.ajax({url:"/admin/api/pickup/"+b+"/report",type:"GET",contentType:"application/json",success:function(a){var b=this.orders.filter(function(b){return b.id===a.id})[0];b&&(b.pickedUp=!1);this.buildOrders()}.bind(this),error:function(a){console.warn(a);c()}.bind(this)})}.bind(this))};
OrderEditor.prototype.filterReports=function(b,a){if(""==a)delete this.reportFilters[b];else{if(("start_date"===b||"end_date"===b)&&!moment(a,"YYYY-MM-DD",!0))return;this.reportFilters[b]=a}this.updateReports()};OrderEditor.prototype.updateReports=function(){this.getReport().then(function(){0<this.reports.length?(document.querySelector(".reports").innerHTML="",this.buildReports()):this.buildEmptyReports()}.bind(this))};
OrderEditor.prototype.previousPage=function(){0<this.filters.page&&(--this.filters.page,this.search().then(function(){document.querySelector(".admin-list.orders").innerHTML="";this.buildOrders();0===this.filters.page&&(document.querySelector(".btn.prev").style.display="none")}.bind(this)))};
OrderEditor.prototype.nextPage=function(){this.filters.page+=1;this.search().then(function(){document.querySelector(".admin-list.orders").innerHTML="";this.buildOrders();0<this.filters.page&&(document.querySelector(".btn.prev").style.display="inline-block")}.bind(this))};
OrderEditor.prototype.previousReportPage=function(){0<this.reportFilters.page&&(--this.reportFilters.page,this.getReport().then(function(){document.querySelector(".admin-list.reports").innerHTML="";this.buildReports();0===this.reportFilters.page&&(document.querySelector(".btn.prev").style.display="none")}.bind(this)))};
OrderEditor.prototype.nextReportPage=function(){this.reportFilters.page+=1;this.getReport().then(function(){document.querySelector(".admin-list.reports").innerHTML="";this.buildReports();0<this.reportFilters.page&&(document.querySelector(".btn.prev").style.display="inline-block")}.bind(this))};
OrderEditor.prototype.previousHistoryPage=function(){0<this.historyFilters.page&&(--this.historyFilters.page,this.getHistory().then(function(){document.querySelector(".admin-list.history").innerHTML="";this.buildHistory();0===this.historyFilters.page&&(document.querySelector(".btn.prev").style.display="none")}.bind(this)))};
OrderEditor.prototype.nextHistoryPage=function(){this.historyFilters.page+=1;this.getHistory().then(function(){document.querySelector(".admin-list.history").innerHTML="";this.buildHistory();0<this.historyFilters.page&&(document.querySelector(".btn.prev").style.display="inline-block")}.bind(this))};
OrderEditor.prototype.filter=function(b,a,c){if(""==a)delete this.filters[b];else{if("start_date"===b||"end_date"===b){if(!moment(a,"YYYY-MM-DD",!0))return;if(moment(a,"YYYY-MM-DD").isAfter(moment())){c.target.value=moment().format("YYYY-MM-DD");return}}this.filters[b]=a}this.updateOrders()};OrderEditor.prototype.updateOrders=function(){this.search().then(function(){0<this.orders.length?(document.querySelector(".orders").innerHTML="",this.buildOrders()):this.buildEmptyResults()}.bind(this))};
OrderEditor.prototype.buildEmptyResults=function(){document.querySelector(".orders").innerHTML=""};OrderEditor.prototype.buildEmptyReports=function(){document.querySelector(".reports").innerHTML=""};OrderEditor.prototype.isComplete=function(b){for(var a=0;a<(b.orderPickups||[]).length;a++)if(moment(b.orderPickups[a].pickupDate).isAfter(moment()))return!1;return!0};
OrderEditor.prototype.buildOrders=function(){for(var b=0;b<this.orders.length;b++){var a=this.orders[b],c=a.orders,d=document.querySelector(".orders"),e=document.querySelector(".order-"+a.id),a={tag:"li",attributes:{className:"info-list order order-"+a.id},children:[{tag:"div",attributes:{className:"order-info date",text:moment(a.pickupDate).format("MM/DD/YYYY")+moment(a.pickupTime).format(" h:mm a")}},{tag:"div",attributes:{className:"order-info location ",text:c.diningHall?c.diningHall.name:""}},
{tag:"div",attributes:{className:"order-info meal",text:c.menu?c.menu.meal.name:""}},{tag:"div",attributes:{className:"order-info user"},children:[{tag:"div",attributes:{text:c.user.huid+" /"}},{tag:"div",attributes:{text:c.user.lastName+", "+c.user.firstName}}]},{tag:"div",attributes:{className:"order-info user"},children:(c.menuSelections||[]).map(function(a){return{tag:"div",attributes:{text:a.menuItem.name}}})},{tag:"div",attributes:{className:"action"},children:[{tag:"div",attributes:{text:a&&
a.pickedUp?"Picked Up":"Not Picked Up"}},{tag:"input",attributes:{type:"button",className:"btn",value:"Change",onClick:this.markNotPickedUp.bind(this,a.id)},condition:function(a){return a.pickedUp}.bind(this,a)}]}]},a=Utils.buildHTML(a);null!==e?(e.parentNode.insertBefore(a,e),e.parentNode.removeChild(e)):d.appendChild(a)}};
OrderEditor.prototype.buildReports=function(){for(var b=0;b<this.reports.length;b++){var a=this.reports[b],c=document.querySelector(".reports"),d=document.querySelector(".report-"+a[0]+"-"+a[3]),a=Utils.buildHTML({tag:"li",attributes:{className:"info-list report report-"+a[0]+"-"+a[3]},children:[{tag:"div",attributes:{className:"order-info name",text:a[1]}},{tag:"div",attributes:{className:"order-info popularity ",text:a[2]}},{tag:"div",attributes:{className:"order-info location",text:a[4]}},{tag:"div",
attributes:{className:"order-info meal",text:a[5]}}]});null!==d?(d.parentNode.insertBefore(a,d),d.parentNode.removeChild(d)):c.appendChild(a)}};
OrderEditor.prototype.buildHistory=function(){for(var b=0;b<this.history.length;b++){var a=this.history[b],c=document.querySelector(".history"),d=document.querySelector(".order-"+a.id),e=a.orderPickups.sort(function(a,b){return moment.utc(a.pickupDate).diff(moment.utc(b.pickupDate))}),a={tag:"li",attributes:{className:"info-list order order-"+a.id},children:[{tag:"div",attributes:{className:"order-info date",text:moment(e[0].pickupDate).format("M/D/YYYY")+" "+moment(e[0].pickupTime).format("h:mm a")}},
{tag:"div",attributes:{className:"order-info location ",text:a.diningHall.name}},{tag:"div",attributes:{className:"order-info meal",text:a.menu.meal.name}},{tag:"div",attributes:{className:"order-info selections"},children:a.menuSelections.map(function(a){return{tag:"div",attributes:{className:"selection",text:a.menuItem.name}}})},{tag:"div",attributes:{className:"order-item repeated",text:1<e.length?"Repeat Until "+moment(e[e.length-1].pickupDate).format("M/D/YYYY"):""}},a.canceled?{tag:"div",attributes:{className:"order-info status",
text:"Canceled on "+moment(a.canceledOn).format("dddd, MMMM DD")}}:this.isComplete(a)?{tag:"div",attributes:{className:"order-info status",text:"Picked Up"}}:{tag:"div",attributes:{className:"order-info status action"},children:[{tag:"a",attributes:{className:"btn",href:"/myhuds/orders/"+a.menu.meal.name.toLowerCase()+"/create/"+a.id,text:"Edit"}},{tag:"a",attributes:{className:"btn",href:"/myhuds/orders/"+a.id+"/cancel",text:"Cancel"}}]}]},a=Utils.buildHTML(a);null!==d?(d.parentNode.insertBefore(a,
d),d.parentNode.removeChild(d)):c.appendChild(a)}};