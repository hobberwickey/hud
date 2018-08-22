//# sourceMappingURL=item-option-editor.js.map
var ItemOptionEditor=function(){this.groups=[];this.names={Dressing:"Salad Dressings",Bread:"Sandwich Breads",Cheese:"Sandwich Cheese"};this.edits=[]};ItemOptionEditor.prototype.refreshOptions=function(){return new Promise(function(a,b){$.ajax({url:"/admin/api/menu-item-options",type:"GET",contentType:"application/json",success:function(b){this.groups=b;a()}.bind(this),error:function(a){console.warn(a);b(a)}.bind(this)})}.bind(this))};
ItemOptionEditor.prototype.saveOption=function(a){return new Promise(function(b,c){$.ajax({url:"/admin/api/menu-item-options/save",type:"POST",contentType:"application/json",data:JSON.stringify(a),success:function(a){b(a)}.bind(this),error:function(a){console.warn(a);c(a)}.bind(this)})}.bind(this))};
ItemOptionEditor.prototype.saveGroup=function(a){return new Promise(function(b,c){$.ajax({url:"/admin/api/menu-item-options/ordering",type:"POST",contentType:"application/json",data:JSON.stringify(a),success:function(a){b(a)}.bind(this),error:function(a){console.warn(a);c(a)}.bind(this)})}.bind(this))};
ItemOptionEditor.prototype.addItem=function(a){var b=this.groups.filter(function(b){return parseInt(b.id,10)===parseInt(a,10)})[0],c=document.querySelector(".group-"+a+" .add-item-input").value;b?this.saveOption({id:null,name:c,menuItemOptionGroups:[{id:a}],position:b.menuItemOptions.length}).then(function(a){b.menuItemOptions.push(a);b.ordering=b.ordering.split(",").concat([a.id]).filter(function(a){return!!a}).join(",");this.saveGroup(b).then(function(a){this.buildOptionGroup(a)}.bind(this))}.bind(this)):
console.log("Couldn't find group with id: ",a)};ItemOptionEditor.prototype.saveOrdering=function(a){var b=this.groups.filter(function(b){return parseInt(b.id,10)===parseInt(a,10)})[0],c=document.querySelectorAll(".group-"+a+" .group-item");if(b){for(var c=Array.prototype.map.call(c,function(a){return isNaN(a.dataset.id,10)?a.dataset.id:parseInt(a.dataset.id,10)}),d=0;d<(b.menuItemOptions||[]).length;d++){var e=b.menuItemOptions[d];e.position=c.indexOf(e.id||e.localId)}b.ordering=c.join(",");this.saveGroup(b)}};
ItemOptionEditor.prototype.openEditOption=function(a,b){this.edits.push(b.id);this.buildOptionGroup(a);document.querySelector(".group-item-"+(b.id||b.localId)+" input").select()};ItemOptionEditor.prototype.editOption=function(a,b){var c=document.querySelector(".group-item-"+b.id+" input");c&&(b.name=c.value,this.edits.splice(this.edits.indexOf(b.id||b.localId),1),this.buildOptionGroup(a,!0),this.saveGroup(a))};
ItemOptionEditor.prototype.cancelEditOption=function(a,b){this.edits.splice(this.edits.indexOf(b.id||b.localId),1);this.buildOptionGroup(a,!0)};
ItemOptionEditor.prototype.removeOption=function(a,b){var c=this.groups.filter(function(b){return parseInt(b.id,10)===parseInt(a,10)})[0],d=c.menuItemOptions.filter(function(a){return a.id===parseInt(b,10)})[0];if(c&&d){var e=c.ordering.split(",").map(function(a){return parseInt(a,10)});e.splice(e.indexOf(parseInt(b,10)),1);c.ordering=e.join(",");d.deleted=!0;this.saveGroup(c).then(function(a){this.buildOptionGroup(a)}.bind(this))}};
ItemOptionEditor.prototype.buildOptionGroup=function(a){var b=document.querySelector(".groups"),c=b.querySelector(".group-"+a.id),d={tag:"li",attributes:{className:"sortable-list group group-"+a.id},children:[{tag:"h2",attributes:{text:this.names[a.name]},children:[]},{tag:"ul",attributes:{className:"sortable-list-items group-items"},children:(a.ordering||"").split(",").map(function(b,c){if(c=a.menuItemOptions.filter(function(a){return parseInt(b,10)===a.id})[0]){var d=Utils.initMove.bind(null,"group",
"group-item",a.id,c.id,this.saveOrdering.bind(this,a.id));return{tag:"li",attributes:{className:(-1===this.edits.indexOf(c.id||c.localId)?"":"editing")+" sortable-list-item group-item group-item-"+c.id,dataId:c.id},children:[{tag:"i",attributes:{className:"fa fa-arrows move",onMouseDown:d},children:[]},{tag:"div",attributes:{className:"group-item-name",text:c.name},children:[]},{tag:"div",attributes:{className:"item-editor"},children:[{tag:"input",attributes:{className:"item-edit",type:"text",value:c.name}},
{tag:"div",attributes:{className:"item-edit-controls"},children:[{tag:"i",attributes:{className:"fa fa-check confirm",onClick:this.editOption.bind(this,a,c)}},{tag:"i",attributes:{className:"fa fa-times cancel",onClick:this.cancelEditOption.bind(this,a,c)}}]}]},{tag:"i",attributes:{className:"fa fa-edit edit",onClick:this.openEditOption.bind(this,a,c)},children:[]},{tag:"i",attributes:{className:"fa fa-trash-o remove",onClick:this.removeOption.bind(this,a.id,c.id),children:[]}}]}}return{tag:"li",
attributes:{className:"empty"}}}.bind(this))},{tag:"div",attributes:{className:"add-item-wrapper"},children:[{tag:"div",attributes:{className:"input-wrapper"},children:[{tag:"input",attributes:{className:"add-item-input",type:"text"},children:[]}]},{tag:"div",attributes:{className:"input-wrapper"},children:[{tag:"input",attributes:{className:"add-item-btn btn",value:"Add",type:"button",onClick:this.addItem.bind(this,a.id)}}]}]}]},d=Utils.buildHTML(d);null!==c?(c.parentNode.insertBefore(d,c),c.parentNode.removeChild(c)):
b.appendChild(d)};ItemOptionEditor.prototype.buildOptionGroups=function(){for(var a=0;a<this.groups.length;a++)this.buildOptionGroup(this.groups[a])};