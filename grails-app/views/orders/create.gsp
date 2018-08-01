<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='public'>
    <div class='content order'>
      <g:form  class='order-form' controller="orders" action="save" id="lunch" method="post">
        <div class='section'>
          <fieldset>
            <legend>Pick up Date</legend>
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
          <fieldset>
            <legend>Pick up Time</legend>
            <g:each in="${ helpers.availableTimes() }" var="time">
              <div class='input-wrapper radio pickupTime'>
                <g:radio name="pickupTime" value="${ time }" readonly="${ true }" checked="${ helpers.isSameDate(time, orderPickup.pickupTime) }"></g:radio>
                <a href="${ helpers.replaceParam('pickupTime', time) }">
                  <label class='btn'><g:formatDate date="${time}" type="time" style="SHORT"/></label>
                </a>
              </div>
            </g:each>
          </fieldset>
        </div>
        
        <div class='section'>
          <fieldset>
            <legend>Pick up Location</legend>
            <g:each status="i" in="${ allLocations }" var="location">
              <div class='input-wrapper checkbox location'>
                <g:radio name="diningHallId" value="${ location.id }" readonly="${ true }" checked="${ order.diningHall != null && order.diningHall.id == location.id }"></g:radio>
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

        <g:if test="${ order.menu != null }">
          <g:each in="${ order.menu.menuSections.sort{a,b -> a.position.compareTo(b.position)} }" var="section">
            <g:if test="${ section.name == 'breakfast' && section.menuItems.size() > 0 }">
              <div class='section'>
                <h2>Select up to 4 items</h2>
                <ul class='menu-list'>
                  <g:each in="${ section.menuItems.sort{a,b -> a.position.compareTo(b.position)} }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
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
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </ul>
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
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </ul>
              </div>
            </g:if>
          </g:each>
        </g:if>

        <input type='submit' class="btn" />
      </g:form>
    </div>
  </div>
</body>
</html>
