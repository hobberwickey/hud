<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='admin'>
    <div class='filters' style='margin-bottom: 5px;'>
      <div class='input-wrapper'>
        <input type='text' name='search' placeholder="HUID or Name" />
        <input type='date' name='start-date' placeholder="Start Date" />
        <input type="date" name="end-date" placeholder="End Date" />
        <select name="locations">
          <option value=''>All Locations</option>
        </select>
        <select name="locations">
          <option value=''>All Meals</option>
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
    })
  </script>
</body>
</html>
