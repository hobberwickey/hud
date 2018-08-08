<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='admin'>
    <h1><span>Orders</span></h2>
    
    <div class='filters' style='margin-bottom: 5px;'>
      <div class='input-wrapper'>
        <input id='huid_search' type='text' name='search' placeholder="HUID or Name" />
        <input id='start_date_search' type='date' name='start-date' placeholder="Start Date" />
        <input id='end_date_search' type="date" name="end-date" placeholder="End Date" />
        <select id='location_search' name="locations">
          <option value=''>All Locations</option>
          <g:each in="${ diningHalls }" var="${ diningHall }">
            <option>${ diningHall.name }</option>
          </g:each>
        </select>
        <select id='meal_search' name="meals">
          <option value=''>All Meals</option>
          <g:each in="${ meals }" var="${ meal }">
            <option>${ meal.name }</option>
          </g:each>
        </select>
      </div>
    </div>
    </div>
    <div class='content'>
      <ul class='content-header order-header'>
        <li><label>Pickup</label></li
        ><li><label>Location</label></li
        ><li><label>Meal</label></li
        ><li><label>HUID/Name</label></li
        ><li><label>Order</label></li
        ><li></li>
      </ul>

      <ul class='admin-list orders'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='order-editor.js' />
  <script>
    $(function(){
      var orderEditor = new OrderEditor();
      
      orderEditor.search({}).then(function(){
        orderEditor.buildOrders();
      }.bind(this));

      document.getElementById("huid_search").addEventListener('change', function(e){
        orderEditor.filterUser(e.target.value)
      })

      document.getElementById("location_search").addEventListener('change', function(e){
        orderEditor.filterLocation(e.target.value)
      })

      document.getElementById("meal_search").addEventListener('change', function(e){
        orderEditor.filterMeal(e.target.value)
      })
    })
  </script>
</body>
</html>
