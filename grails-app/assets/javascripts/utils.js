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

  getParent: function(element, selector) {

  }
}