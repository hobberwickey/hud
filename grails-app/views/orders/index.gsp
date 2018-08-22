<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='admin'>
    <h1>Orders</h1>
    
    <div class='filters order-filters' style='margin-bottom: 5px;'>
      <div class='input-wrapper'>
        <input id='huid_search' class='search' type='text' name='search' placeholder="HUID or Name" />
        <label for='start_date_search'>From</label>
        <input id='start_date_search' type='date' name='start-date' placeholder="Start Date" />
        <label for='end_date_search'>To</label>
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
        <select id='status_search' name="status">
          <option value=''>All Statuses</option>
          <option value='1'>Picked Up</option>
          <option value='0'>Not Picked Up</option>
        </select>
      </div>
    </div>
    <div class='content'>
      <ul class='content-header order-header'>
        <li><label>Date & Time</label></li
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
        orderEditor.filter("user", e.target.value)
      })

      document.getElementById("location_search").addEventListener('change', function(e){
        orderEditor.filter("location", e.target.value)
      })

      document.getElementById("meal_search").addEventListener('change', function(e){
        orderEditor.filter("meal", e.target.value)
      })

      document.getElementById("start_date_search").addEventListener('change', function(e){
        orderEditor.filter("start_date", e.target.value)
      })

      document.getElementById("end_date_search").addEventListener('change', function(e){
        orderEditor.filter("end_date", e.target.value)
      })

      document.getElementById("status_search").addEventListener('change', function(e){
        orderEditor.filter("status", e.target.value)
      })
    })
  </script>
</body>
</html>
