var Utils = {
  createElement: function(tag, attributes) {
    var el = document.createElement(tag)

    for (var x in attributes) {
      if (/^on[A-Z]/g.test(x)){
        // var ev = x.charAt(2).toLowerCase() + x.substr(3, x.length);
        el.addEventListener(x.substr(2, x.length).toLowerCase(), attributes[x], false);
      } else if (x === "text") {
        var text = document.createTextNode(attributes[x]); 
        el.appendChild(text);
      } else if (/^data[A-Z]/g.test(x)) {
        var key = x.charAt(4).toLowerCase() + x.substr(5, x.length);
        el.dataset[key] = attributes[x];
      } else {
        el[x] = attributes[x];
      }
    }

    return el
  },

  /*
    Format Example: 

    {tag: "ul", attributes: {className: "list"}, children: [
      {tag: "li", attributes: {text: "One"}, children: []},
      {tag: "li", attributes: {text: "Two"}, children: []}
    ]}
  */

  buildHTML: function(elements) {
    var fn = function(element){
      if (!element) {
        console.log(elements)
        return Utils.createElement("div", {className: "error", text: "Could not create element"})
      }

      var parent = Utils.createElement(element.tag, element.attributes);

      if (!!element.children && element.children.length){
        for (var i=0; i<element.children.length; i++){
          parent.appendChild(fn(element.children[i]));
        }
      }
      
      return parent
    }.bind(this);
    
    return fn(elements)
  },

  genUUID: function() {
    function s4() {
      return Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);
    }

    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
  },

  /*
    Drag and Drop reordering for html with the following struct
    
    <ul class='[parentClass] [parentClass]-[parentId]'>
      <li class='[targetClass] [targetClass]-[targetId]' data-id='[targetId]'>Example 1</li>
      <li class='[targetClass] [targetClass]-[targetId]' data-id='[targetId]'>Example 2</li>
    </ul>
  */
  initMove: function(parentClass, targetClass, parentId, targetId, onComplete, e){      
    // e.preventDefault();
    e.stopPropagation();
    
    var onDrag = function(e){
      if (e.screenY === 0) return;

      var list    = document.querySelector("." + parentClass + "-" + parentId),
          items   = list.querySelectorAll("." + targetClass),
          scroll  = window.scrollY || document.documentElement.scrollTop,
          coords  = list.getBoundingClientRect()
          boxTop  = coords.top,
          boxBtm  = coords.top + coords.height,
          eTop    = e.pageY - (scroll + boxTop),
          eBtm    = e.pageY - (scroll + boxBtm),
          tops    = Array.prototype.map.call(items, function(item){ return [item.offsetTop + (item.offsetHeight / 2), item.dataset.id] });
      
      var sibling = null;    
      for (var i=0; i<tops.length; i++){
        if (i === 0){
          if (eTop < tops[0][0]){
            sibling = document.querySelector("." + parentClass + "-" + parentId + " ." + targetClass + "-" + tops[0][1]);
            break;
          }
        } else {
          if (tops[i - 1][0] < eTop && tops[i][0] > eTop){
            sibling = document.querySelector("." + parentClass + "-" + parentId + " ." + targetClass + "-" + tops[i][1]);
            break;
          }
        }
      }

      if (sibling !== null){
        if (sibling.dataset.id !== targetId){
          sibling.parentNode.insertBefore(dragTarget, sibling);
        }
      } else {
        if (tops[tops.length - 1][1] !== targetId){
          dragTarget.parentNode.appendChild(dragTarget)
        }
      }
    }

    var onDragEnd = function(){
      dragTarget.removeEventListener("drag", onDrag);
      dragTarget.removeEventListener("dragend", onDragEnd);
      
      onComplete();

      // var parent = list.filter(function(p){ return p.id === parentId })[0];
      //     children = document.querySelectorAll("." + parentClass + "-" +  + " ." + targetClass),
      //     ordering = Array.prototype.map.call(children, function(item){ return item.dataset.id }).join(",");

      // menu.ordering = ordering;
      // me.saveMenu(menu);
    }
        
    console.log(parentClass, parentId, targetClass, targetId)
    var dragTarget = document.querySelector("." + parentClass + "-" + parentId + " ." + targetClass + "-" + targetId);
        dragTarget.draggable = "true";
        dragTarget.addEventListener("drag", onDrag, false);
        dragTarget.addEventListener("dragend", onDragEnd, false);
  }
}