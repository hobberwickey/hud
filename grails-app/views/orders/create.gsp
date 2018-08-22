<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='public'>
    <h1>Order ${ meal.name }</h1>
    <div class='content order'>
      <form  class='order-form' action="/myhuds/orders/${ meal.name.toLowerCase() }/save/${ order.id == null ? '' : order.id }" method="post">
        <input name='id' type="hidden" value="${ order.id }" />
        <div class='section'>
          <p>
            Place your order before the end of staffed service for the next day. 
            You may order one meal-to-go per meal period. <strong>Please note: ordering 
            a Meal-to-Go will prevent you from eating a regular meal in a 
            dining hall at the designated meal period without special permission from a dining 
            hall manager.</strong> Bag meals are only available for pick-up when the dining 
            halls are open.
          </p>
          <p>
            Please Note: Upperclassmen may not order bag meals from Annenberg, and should instead use fly by.
          </p>
        </div>
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
                <g:radio name="pickupTime" value="${ time }" readonly="${ true }" checked="${ helpers.isSameTime(time, orderPickup.pickupTime) }"></g:radio>
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

        <div class='section repeat'>
          <fieldset>
            <legend>Repeat Order</legend>
            <div class='input-wrapper radio pickupTime'>
              <g:radio name="repeated" value="${ true }" checked="${ order.orderPickups.size() > 1 }"></g:radio>
              <label class='btn'>Yes</label>
            </div>
            <div class='input-wrapper radio pickupTime'>
              <g:radio name="repeated" value="${ false }" checked="${ order.orderPickups.size() < 2 }"></g:radio>
              <label class='btn'>No</label>
            </div>
            <div class='input-wrapper repeat-date'>
              <input type='date' name="repeatEndDate" value="${ order.orderPickups.size() > 0 ? helpers.lastPickup(order).pickupDate.format('yyyy-MM-dd') : '' }"/>
            </div>
          </fieldset>
        </div>

        <div class='menu' data-id="${ order.menu == null ? '' : order.menu.id }">
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
                    <ul class='menu-list breakfast'>
                      <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                        <li>
                          <div class='menu-item input-wrapper radio'>
                            <g:if test="${ helpers.hasItem(order, item) }">
                              <input class='breakfast-item' type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                            </g:if>
                            <g:else>
                              <input class='breakfast-item' type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />
                            </g:else>
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
                                                <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                  <input class='option' type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" checked='checked' />
                                                </g:if>
                                                <g:else>
                                                  <input class='option' type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                                </g:else>
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
                          <g:if test="${ helpers.hasItem(order, item) }">
                            <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                          </g:if>
                          <g:else>
                            <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />
                          </g:else>
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
                                              <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                <input type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" checked="checked" />
                                              </g:if>
                                              <g:else>
                                                <input type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:else>
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
                          <g:if test="${ helpers.hasItem(order, item) }">
                            <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                          </g:if>
                          <g:else>
                            <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          </g:else>
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
                                              <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                <input type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" checked="checked" />
                                              </g:if>
                                              <g:else>
                                                <input type='radio' name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:else>
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
                          <g:if test="${ helpers.hasSnackItem(order, item, 1) }">
                            <input type='radio' name="${ 'snack.1.section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                          </g:if>
                          <g:else>
                            <input type='radio' name="${ 'snack.1.section.' + section.id + '.menuItems' }"  value="${ item.id }" />
                          </g:else>
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
                                              <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                <input type='radio' name="${ 'snack.1.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:if>
                                              <g:else>
                                                <input type='radio' name="${ 'snack.1.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:else>
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
                          <g:if test="${ helpers.hasSnackItem(order, item, 2) }">
                            <input type='radio' name="${ 'snack.2.section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                          </g:if>
                          <g:else>
                            <input type='radio' name="${ 'snack.2.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          </g:else>
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
                                              <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                <input type='radio' name="${ 'snack.2.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" checked="checked" />
                                              </g:if>
                                              <g:else>
                                                <input type='radio' name="${ 'snack.2.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:else>
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
                          <g:if test="${ helpers.hasSnackItem(order, item, 3) }">
                            <input type='radio' name="${ 'snack.3.section.' + section.id + '.menuItems' }"  value="${ item.id }" checked="checked" />           
                          </g:if>
                          <g:else>
                            <input type='radio' name="${ 'snack.3.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                          </g:else>
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
                                              <g:if test="${ helpers.hasItemOption(order, item, opt) }">
                                                <input name="${ 'snack.3.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" checked="checked" />
                                              </g:if>
                                              <g:else>
                                                <input name="${ 'snack.3.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }" />
                                              </g:else>
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

          <div class='section'>
            <p>
              Please consumer or refrigerate your bag meal within 4 hours of pick-up. 
              Before placing your order, please inform your server if a person in 
              your party has a food allergy.
            </p>
          </div>
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
          if (inputs[i].className !== "option"){
            inputs[i].addEventListener("change", this.updateMenu.bind(this), false);
          }
        }

        for (var i=0; i<buttons.length; i++){
          var a = buttons[i].parentNode,
              w = a.parentNode;

          w.appendChild(buttons[i]);
          w.removeChild(a);
        }

        [].forEach.call(form.querySelectorAll(".breakfast-item"), function(input){
          input.addEventListener("change", function(e){
            if (form.querySelectorAll(".breakfast-item:checked").length > 4){
              e.target.checked = false;
            }
          });
        });
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
            var menuId = !!this.data.order && !!this.data.order.menu ? this.data.order.menu.id || null : null,
                newId = !!resp.order.menu ? resp.order.menu.id || null : null;

            var menuId = +document.querySelector(".menu").dataset["id"];
            if (newId !== null && newId !== menuId) {
              this.data = resp;
              this.buildMenu();
            } else {
              this.data = resp;
            }

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
        var isBreakfast = section.name === "breakfast";
        section.menuItems.sort(function(a, b){ return b.position < a.position });
        return {tag: "div", attributes: {className: "section"}, children: [
          {tag: "h2", attributes: {text: section.name === "snacks" ? names[section.name][snackIndex] : names[section.name]}},
          {tag: "ul", attributes: {className: "menu-list"}, children: (section.menuItems || []).map(function(item){
            var itemName = this.buildItemName(section, item, snackIndex)

            item.menuItemOptionGroups.sort(function(a, b){ 
              return optionsOrder.indexOf(b.name) < optionsOrder.indexOf(a.name) 
            });

            return {tag: "li", attributes: {}, children: [
              {tag: "div", attributes: {className: "menu-item input-wrapper " + (isBreakfast ? "checkbox" : "radio")}, children: [
                {tag: "input", attributes: {className: (isBreakfast ? "breakfast-item" : ""), type: (isBreakfast ? "checkbox" : "radio"), name: itemName, value: item.id }},
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
            
            [].forEach.call(html.querySelectorAll(".breakfast-item"), function(input){
              input.addEventListener("change", function(e){
                if (html.querySelectorAll(".breakfast-item:checked").length > 4){
                  e.target.checked = false;
                }
              });
            });
          }
        }
      }

      var orderController = new OrderController();
    })


  </script>
</body>
</html>
