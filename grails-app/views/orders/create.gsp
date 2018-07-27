<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Dining Halls</title>
</head>
<body>
  <div class='public'>
    <h1><span>Menus</span> <a href="/admin/menus/new"><i class='fa fa-plus' alt='Add Menu'></i></a></h2>
    <div class='content order'>
      <g:form  class='order-form' controller="orders" action="saveLunch" method="post">
        <div class='section'>
          <fieldset>
            <legend>Pick up Date</legend>
            <g:each in="${ dates }" var="date">
              <div class='input-wrapper radio pickupDate'>
                <g:radio name="pickupDate" value="${ date }" readonly="${ true }" checked="${ helpers.isSameDate(date, selectedDate) }"></g:radio>
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
            <g:each in="${ times }" var="time">
              <div class='input-wrapper radio pickupTime'>
                <g:radio name="pickupTime" value="${ time }" readonly="${ true }" checked="${ helpers.isSameDate(time, selectedTime) }"></g:radio>
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
            <g:each status="i" in="${ locations }" var="location">
              <div class='input-wrapper checkbox location'>
                <g:radio name="diningHallId" value="${ location.id }" readonly="${ true }" checked="${ selectedLocation != null && selectedLocation.id == location.id }"></g:radio>
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

        <g:if test="${ selectedMenu != null }">
          <div class='section'>
            <h2>Select a Sandwich or Salad</h2>
            <ul class='menu-list'>
              <g:each in="${ selectedMenu.menuSections }" var="section">
                <g:if test="${ section.name == 'sandwiches-salads' }">
                  <g:each in="${ section.menuItems }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='radio' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                          <div class='menu-item-options'>
                            <g:each in="${ item.menuItemOptionGroups }" var="group">
                              <ul class='options-group'>
                                <h3>Select a ${ group.name }</h3>
                                <g:each in="${ group.menuItemOptions }" var="opt">
                                  <li class='option'>
                                    <div class='input-wrapper'>
                                      <g:radio name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                      <label class='btn'>${ group.id } ${ opt.name }</label>
                                    </div>
                                  </li>
                                </g:each>
                              </ul>
                            </g:each>
                          </div>
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </g:if>
              </g:each>
            </ul>
          </div>

          <!-- <div class='section'>
            <h2>Add A Beverage</h2>
            <ul class='menu-list'>
              <g:each in="${ selectedMenu.menuSections }" var="section">
                <g:if test="${ section.name == 'beverages' }">
                  <g:each in="${ section.menuItems }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='checkbox' name="${ 'section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                          <div class='menu-item-options'>
                            <g:each in="${ item.menuItemOptionGroups }" var="group">
                              <ul class='options-group'>
                                <h3>Select a ${ group.name }</h3>
                                <g:each in="${ group.menuItemOptions }" var="opt">
                                  <li class='option'>
                                    <div class='input-wrapper'>
                                      <g:radio name="${ 'group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                      <label class='btn'>${ opt.name }</label>
                                    </div>
                                  </li>
                                </g:each>
                              </ul>
                            </g:each>
                          </div>
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </g:if>
              </g:each>
            </ul>
          </div>

          <div class='section'>
            <h2>Add First Snack</h2>
            <ul class='menu-list'>
              <g:each in="${ selectedMenu.menuSections }" var="section">
                <g:if test="${ section.name == 'snacks' }">
                  <g:each in="${ section.menuItems }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='checkbox' name="${ 'snack.1.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                          <div class='menu-item-options'>
                            <g:each in="${ item.menuItemOptionGroups }" var="group">
                              <ul class='options-group'>
                                <h3>Select a ${ group.name }</h3>
                                <g:each in="${ group.menuItemOptions }" var="opt">
                                  <li class='option'>
                                    <div class='input-wrapper'>
                                      <g:radio name="${ 'snack.1.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                      <label class='btn'>${ opt.name }</label>
                                    </div>
                                  </li>
                                </g:each>
                              </ul>
                            </g:each>
                          </div>
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </g:if>
              </g:each>
            </ul>
          </div>

          <div class='section'>
            <h2 class='opional'>Add Second Snack (Optional)</h2>
            <ul class='menu-list'>
              <g:each in="${ selectedMenu.menuSections }" var="section">
                <g:if test="${ section.name == 'snacks' }">
                  <g:each in="${ section.menuItems }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='checkbox' name="${ 'snack.2.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                          <div class='menu-item-options'>
                            <g:each in="${ item.menuItemOptionGroups }" var="group">
                              <ul class='options-group'>
                                <h3>Select a ${ group.name }</h3>
                                <g:each in="${ group.menuItemOptions }" var="opt">
                                  <li class='option'>
                                    <div class='input-wrapper'>
                                      <g:radio name="${ 'snack.2.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                      <label class='btn'>${ opt.name }</label>
                                    </div>
                                  </li>
                                </g:each>
                              </ul>
                            </g:each>
                          </div>
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </g:if>
              </g:each>
            </ul>
          </div>

          <div class='section'>
            <h2 class='optional'>Add Third Snack (Optional)</h2>
            <ul class='menu-list'>
              <g:each in="${ selectedMenu.menuSections }" var="section">
                <g:if test="${ section.name == 'snacks' }">
                  <g:each in="${ section.menuItems }" var="item">
                    <li>
                      <div class='menu-item input-wrapper radio'>
                        <input type='checkbox' name="${ 'snack.3.section.' + section.id + '.menuItems' }"  value="${ item.id }" />           
                        <label class='btn'>${ item.name }</label>
                      
                        <g:if test="${ item.menuItemOptionGroups.size() > 0 }">
                          <div class='menu-item-options'>
                            <g:each in="${ item.menuItemOptionGroups }" var="group">
                              <ul class='options-group'>
                                <h3>Select a ${ group.name }</h3>
                                <g:each in="${ group.menuItemOptions }" var="opt">
                                  <li class='option'>
                                    <div class='input-wrapper'>
                                      <g:radio name="${ 'snack.3.group.' + group.id + '.menuItem.' + item.id }"  value="${ opt.id }"></g:radio>
                                      <label class='btn'>${ opt.name }</label>
                                    </div>
                                  </li>
                                </g:each>
                              </ul>
                            </g:each>
                          </div>
                        </g:if>
                      </div>
                    </li>
                  </g:each>
                </g:if>
              </g:each>
            </ul>
          </div> -->

        </g:if>

        <input type='submit' class="btn" />
      </g:form>
    </div>
  </div>
</body>
</html>
