<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='admin'>
    <h1>Orders History</h1>
    <div class='content'>
      <ul class='content-header history-header'>
        <li><label>Pickup</label></li
        ><li><label>Location</label></li
        ><li><label>Meal</label></li
        ><li><label>Order</label></li
        ><li><label>Repeat</label></li
        ><li><label>Status</label></li>
      </ul>

      <ul class='admin-list history'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='order-editor.js' />
  <script>
    $(function(){
      var orderEditor = new OrderEditor();
      
      orderEditor.getHistory().then(function(){
        orderEditor.buildHistory();
      }.bind(this));
    })
  </script>
</body>
</html>
