<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='public'>
    <div class='content order'>
      <form  class='order-form' action="/myhuds/orders/${ meal.name.toLowerCase() }/save/${ order.id == null ? '' : order.id }" mealType="" method="post">
        <div class='section'>
          <fieldset>
            <legend>Pick up Date</legend>
            <g:if test="${ errors['pickupDate'].size() > 0 }">
              <ul class='errors'>
                <g:each in="${ errors['pickupDate'] }" var="err">
                  <li>${ err }</li>
                </g:each>
              </ul>
            </g:if>
            <g:each in="${ helpers.availableDates() }" var="date">
              <div class='input-wrapper radio pickupDate'>
                <g:radio name="pickupDate" value="${ date }" readonly="${ true }" checked="${ helpers.isSameDate(date, orderPickup.pickupDate) }"></g:radio>
                <a href="${ helpers.replaceParam('pickupDate', date) }">
                  <label class='btn'><g:formatDate date="${date}" type="date" style="MEDIUM"/></label>
                </a>
              </div>
            </g:each>
          </fieldset>
        </div>

        <div class='section'>
          <fieldset class='pickup-times'>
            <legend>Pick up Time</legend>
            <g:if test="${ errors['pickupTime'].size() > 0 }">
              <ul class='errors'>
                <g:each in="${ errors['pickupTime'] }" var="err">
                  <li>${ err }</li>
                </g:each>
              </ul>
            </g:if>
            <g:each in="${ helpers.availableTimes() }" var="time">
              <div class='input-wrapper radio pickupTime'>
                <g:radio name="pickupTime" value="${ time }" readonly="${ true }" checked="${ helpers.isSameDate(time, orderPickup.pickupTime) }"></g:radio>
                <a href="${ helpers.replaceParam('pickupTime', time) }">
                  <label class='btn'><g:formatDate date="${time}" type="time" style="SHORT"/></label>
                </a>
              </div>
            </g:each>
            <div class='btn show-more'>Show All Times</div>
            <div class='btn show-less'>Show Less Times</div>
          </fieldset>
        </div>
        
        <div class='section'>
          <fieldset>
            <legend>Pick up Location</legend>
            <g:if test="${ errors['diningHall'].size() > 0 }">
              <ul class='errors'>
                <g:each in="${ errors['diningHall'] }" var="err">
                  <li>${ err }</li>
                </g:each>
              </ul>
            </g:if>
            <g:each status="i" in="${ allLocations }" var="location">
              <div class='input-wrapper checkbox location'>
                <g:radio name="diningHallId" value="${ location.id }" readonly="${ true }" disabled="${ helpers.locationAvailable(availableLocations, location) }" checked="${ order.diningHall != null && order.diningHall.id == location.id }"></g:radio>
                <a href="${ helpers.replaceParam('diningHallId', location.id) }">
                  <label class='btn'>${ location.name }</label>
                </a>
              </div>
            </g:each>
          </fieldset>
        </div>

        <div class='section'>
          <fieldset>
            <legend>Repeat Order</legend>
            <div class='input-wrapper radio pickupTime'>
              <g:radio name="repeated" value="${ true }"></g:radio>
              <label class='btn'>Yes</label>
            </div>
            <div class='input-wrapper radio pickupTime'>
              <g:radio name="repeated" value="${ false }"></g:radio>
              <label class='btn'>No</label>
            </div>
            <div class='input-wrapper repeat-date'>
              <input type='date' name="repeatEndDate" />
            </div>
          </fieldset>
        </div>

        <div class='menu'>
          <g:if test="${ order.menu != null }">
            <g:each in="${ order.menu.menuSections.sort{a,b -> a.position.compareTo(b.position)} }" var="section">
              <g:if test="${ section.name == 'breakfast' && section.menuItems.size() > 0 }">
                <div class='section'>
                  <fieldset>
                    <legend>Select up to 4 items</legend>
                    <g:if test="${ errors['breakfast'].size() > 0 }">
                      <ul class='errors'>
                        <g:each in="${ errors['breakfast'] }" var="err">
                          <li>${ err }</li>
                        </g:each>
                      </ul>
                    </g:if>
                    <ul class='menu-list'>
                      <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                        <li>
                          <div class='menu-item input-wrapper radio'>
                            <input type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                            <label class='btn'>${ item.name }</label>
                          
                            <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                              <div>
                                <div class='menu-item-options'>
                                  <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                    <g:each in="${ item.menuItemOptionGroups }" var="group">
                                      <g:if test="${ group.name == groupType }">
                                        <ul class='options-group'>
                                          <h3>Select a ${ group.name }</h3>
                                          <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                            <li class='option'>
                                              <div class='input-wrapper'>
                                                <g:radio name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                                <label class='btn'>${ group.id } ${ opt.name }</label>
                                              </div>
                                            </li>
                                          </g:each>
                                        </ul>
                                      </g:if>
                                    </g:each>
                                  </g:each>
                                </div>
                              </div>
                            </g:if>
                          </div>
                        </li>
                      </g:each>
                    </ul>
                  </fieldset>
                </div>
              </g:if>

              <g:if test="${ section.name == 'sandwiches-salads' && section.menuItems.size() > 0 }">
                <div class='section'>
                  <h2>Select a Sandwich or Salad</h2>
                  <ul class='menu-list'>
                    <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                      <li>
                        <div class='menu-item input-wrapper radio'>
                          <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          <label class='btn'>${ item.name }</label>
                        
                          <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                            <div>
                              <div class='menu-item-options'>
                                <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                  <g:each in="${ item.menuItemOptionGroups }" var="group">
                                    <g:if test="${ group.name == groupType }">
                                      <ul class='options-group'>
                                        <h3>Select a ${ group.name }</h3>
                                        <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                          <li class='option'>
                                            <div class='input-wrapper'>
                                              <g:radio name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                              <label class='btn'>${ opt.name }</label>
                                            </div>
                                          </li>
                                        </g:each>
                                      </ul>
                                    </g:if>
                                  </g:each>
                                </g:each>
                              </div>
                            </div>
                          </g:if>
                        </div>
                      </li>
                    </g:each>
                  </ul>
                </div>
              </g:if>

              <g:if test="${ section.name == 'beverages' && section.menuItems.size() > 0 }">
                <div class='section'>
                  <h2>Add A Beverage</h2>
                  <ul class='menu-list'>  
                    <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                      <li>
                        <div class='menu-item input-wrapper radio'>
                          <input type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          <label class='btn'>${ item.name }</label>
                        
                          <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                            <div>
                              <div class='menu-item-options'>
                                <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                  <g:each in="${ item.menuItemOptionGroups }" var="group">
                                    <g:if test="${ group.name == groupType }">
                                      <ul class='options-group'>
                                        <h3>Select a ${ group.name }</h3>
                                        <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                          <li class='option'>
                                            <div class='input-wrapper'>
                                              <g:radio name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                              <label class='btn'>${ opt.name }</label>
                                            </div>
                                          </li>
                                        </g:each>
                                      </ul>
                                    </g:if>
                                  </g:each>
                                </g:each>
                              </div>
                            </div>
                          </g:if>
                        </div>
                      </li>
                    </g:each>
                  </ul>
                </div>
              </g:if>

              <g:if test="${ section.name == 'snacks' && section.menuItems.size() > 0 }">
                <div class='section'>
                  <h2>Add First Snack</h2>
                  <ul class='menu-list'>
                    <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                      <li>
                        <div class='menu-item input-wrapper radio'>
                          <input type='checkbox' name="${ 'snack.1.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          <label class='btn'>${ item.name }</label>
                        
                          <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                            <div>
                              <div class='menu-item-options'>
                                <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                  <g:each in="${ item.menuItemOptionGroups }" var="group">
                                    <g:if test="${ group.name == groupType }">
                                      <ul class='options-group'>
                                        <h3>Select a ${ group.name }</h3>
                                        <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                          <li class='option'>
                                            <div class='input-wrapper'>
                                              <g:radio name="${ 'snack.1.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                              <label class='btn'>${ opt.name }</label>
                                            </div>
                                          </li>
                                        </g:each>
                                      </ul>
                                    </g:if>
                                  </g:each>
                                </g:each>
                              </div>
                            </div>
                          </g:if>
                        </div>
                      </li>
                    </g:each>
                  </ul>
                </div>

                <div class='section'>
                  <h2>Add Second Snack (Optional)</h2>
                  <ul class='menu-list'>
                    <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                      <li>
                        <div class='menu-item input-wrapper radio'>
                          <input type='checkbox' name="${ 'snack.2.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          <label class='btn'>${ item.name }</label>
                        
                          <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                            <div>
                              <div class='menu-item-options'>
                                <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                  <g:each in="${ item.menuItemOptionGroups }" var="group">
                                    <g:if test="${ group.name == groupType }">
                                      <ul class='options-group'>
                                        <h3>Select a ${ group.name }</h3>
                                        <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                          <li class='option'>
                                            <div class='input-wrapper'>
                                              <g:radio name="${ 'snack.2.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                              <label class='btn'>${ opt.name }</label>
                                            </div>
                                          </li>
                                        </g:each>
                                      </ul>
                                    </g:if>
                                  </g:each>
                                </g:each>
                              </div>
                            </div>
                          </g:if>
                        </div>
                      </li>
                    </g:each>
                  </ul>
                </div>

                <div class='section'>
                  <h2>Add Third Snack (Optional)</h2>
                  <ul class='menu-list'>
                    <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                      <li>
                        <div class='menu-item input-wrapper radio'>
                          <input type='checkbox' name="${ 'snack.3.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          <label class='btn'>${ item.name }</label>
                        
                          <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                            <div>
                              <div class='menu-item-options'>
                                <g:each in="${ ['Dressing', 'Bread', 'Cheese'] }" var="groupType">
                                  <g:each in="${ item.menuItemOptionGroups }" var="group">
                                    <g:if test="${ group.name == groupType }">
                                      <ul class='options-group'>
                                        <h3>Select a ${ group.name }</h3>
                                        <g:each in="${ group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)} }" var="opt">
                                          <li class='option'>
                                            <div class='input-wrapper'>
                                              <g:radio name="${ 'snack.3.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                              <label class='btn'>${ opt.name }</label>
                                            </div>
                                          </li>
                                        </g:each>
                                      </ul>
                                    </g:if>
                                  </g:each>
                                </g:each>
                              </div>
                            </div>
                          </g:if>
                        </div>
                      </li>
                    </g:each>
                  </ul>
                </div>
              </g:if>
            </g:each>
          </g:if>
        </div>

        <input type='submit' class="btn" />
      </form>
    </div>
  </div>

  <script>
    $(function(){
      var OrderController = function(){
        this.data = {};
        this.selections = [];

        var form = document.querySelector("form"),
            inputs = document.querySelectorAll(".input-wrapper input[type=radio]"),
            buttons = document.querySelectorAll(".input-wrapper a label");

        // form.addEventListener("submit", function(e){ e.preventDefault() });

        for (var i=0; i<inputs.length; i++) {
          inputs[i].readOnly = false;
          inputs[i].addEventListener("change", this.updateMenu.bind(this), false);
        }

        for (var i=0; i<buttons.length; i++){
          var a = buttons[i].parentNode,
              w = a.parentNode;

          w.appendChild(buttons[i]);
          w.removeChild(a);
        }
      }

      OrderController.prototype.updateMenu = function(){
        var form = document.querySelector("form"),
            params = { 
              pickupDate: form.elements.pickupDate.value,
              pickupTime: form.elements.pickupTime.value,
              diningHallId: form.elements.diningHallId.value
            }

        $.ajax({
          url: "/myhuds/orders/${ meal.name.toLowerCase() }/create" + "?" + $.param(params),
          success: function(resp) {
            this.data = resp;
            this.buildMenu();
            this.disableLocations();
          }.bind(this), 
          error: function(resp) {

          }.bind(this)
        })
      }

      OrderController.prototype.disableLocations = function(){
        var availableIds = (this.data.availableLocations || []).map(function(l){ return l.id }),
            locationControls = document.querySelectorAll(".location input[type=radio]");

        for (var i=0; i<locationControls.length; i++){
          if (availableIds.indexOf(parseInt(locationControls[i].value, 10)) === -1) {
            locationControls[i].disabled = true;
          } else {
            locationControls[i].disabled = false;
          }
        }
      }

      OrderController.prototype.buildItemName = function(section, item, snackIndex) {
        var itemName = "section." + section.id + ".menuItems";
            if (section.name === "snacks"){ 
              itemName = "snack." + snackIndex + "." + itemName;
            }
        
        return itemName;
      }

      OrderController.prototype.buildGroupName = function(section, group, item, snackIndex) {
        var groupName = 'group.' + group.id + '.menuItem.' + item.id
            if (section.name === "snacks"){ 
              groupName = "snack." + snackIndex + "." + groupName;
            }
                    
        return groupName;
      }

      OrderController.prototype.buildSection = function(section, snackIndex) {
        var names = {
          "breakfast": "Select up to 4 Items",
          "sandwiches-salads": "Sandwiches and Salads",
          "beverages": "Beverage (Optional)",
          "snacks": ["First Snack (Optional)", "Select 2nd Snack (Optional)", "Select 3rd Snack (Optional)"]
        };

        var optionsOrder = ['Dressing', 'Bread', 'Cheese'];

        section.menuItems.sort(function(a, b){ return a.position < b.position });
        return {tag: "div", attributes: {className: "section"}, children: [
          {tag: "h2", attributes: {text: section.name === "snacks" ? names[section.name][snackIndex] : names[section.name]}},
          {tag: "ul", attributes: {className: "menu-list"}, children: (section.menuItems || []).map(function(item){
            var itemName = this.buildItemName(section, item, snackIndex)

            item.menuItemOptionGroups.sort(function(a, b){ 
              return optionsOrder.indexOf(b.name) < optionsOrder.indexOf(a.name) 
            });

            return {tag: "li", attributes: {}, children: [
              {tag: "div", attributes: {className: "menu-item input-wrapper checkbox"}, children: [
                {tag: "input", attributes: {type: "checkbox", name: itemName, value: item.id }},
                {tag: "label", attributes: {className: "btn", text: item.name}},
                {tag: "div", attributes: {}, children: (item.menuItemOptionGroups || []).map(function(group){
                  if (group.menuItemOptions.length === 0) return null;

                  group.menuItemOptions.sort(function(a, b){ return b.position < a.position })
                  return {tag: "div", attributes: {className: "menu-item-options"}, children: [
                    {tag: "ul", attributes: {className: "options-group"}, children: [
                      {tag: "h3", attributes: {text: "Select a " + group.name }}
                    ].concat(group.menuItemOptions.map(function(option){
                      return {tag: "li", attributes: {className: "option"}, children: [
                        {tag: "div", attributes: {className: "input-wrapper"}, children: [
                          {tag: "input", attributes: {type: "radio", name: this.buildGroupName(section, group, item, snackIndex), value: option.id}},
                          {tag: "label", attributes: {text: option.name}}
                        ]}
                      ]}
                    }.bind(this)))}
                  ]}
                }.bind(this)).filter(function(g){ return !!g })}
              ]}
            ]}
          }.bind(this))}
        ]}
      }

      OrderController.prototype.buildMenu = function() {
        if (!!this.data.order && !!this.data.order.menu) {
          var menu = this.data.order.menu,
              sections = [];

          if (!!menu) {
            menu.menuSections.sort(function(a, b){
              return b.position < a.position;
            })

            menu.menuSections.filter(function(section){
              return (section.menuItems || []).length > 0;
            }).map(function(section){
              if (section.name === "snacks") {
                for (var i=0; i<3; i++){
                  sections.push(this.buildSection(section, i));
                }
              } else {
                sections.push(this.buildSection(section, null))
              }
            }.bind(this))

            var el = document.querySelector(".menu"),
                html = Utils.buildHTML({tag: "div", attributes: {className: "menu"}, children: sections})
            
            el.parentNode.insertBefore(html, el);
            el.parentNode.removeChild(el);
          }
        }
      }

      var orderController = new OrderController();
    })


  </script>
</body>
</html>
