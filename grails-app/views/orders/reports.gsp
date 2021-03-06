<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Orders</title>
</head>
<body>
  <div class='admin'>
    <h1>Order Reports</h1>
    
    <div class='filters' style='margin-bottom: 5px;'>
      <div class='input-wrapper'>
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
    <div class='count'></div>
    <div class='content'>
      <ul class='content-header report-header'>
        <li id='item_name' class='desc'><label>Item <span></span></label></li
        ><li id='item_count' class='desc'><label>Count <span></span></label></li
        ><li><label>Location</label></li
        ><li><label>Meal Type</label></li>
      </ul>

      <ul class='admin-list reports'>
        
      </ul>
      <div class='pagination'>
        <input type='button' class='btn prev' value="Previous" />
        <input type='button' class='btn next' value="Next" />
      </div> 

    </div>
  </div>

  <asset:javascript src='order-editor.js' />
  <script>
    $(function(){
      var orderEditor = new OrderEditor();
      
      orderEditor.getReport({}).then(function(){
        orderEditor.buildReports();
      }.bind(this));

      document.getElementById("location_search").addEventListener('change', function(e){
        orderEditor.filterReports("location", e.target.value)
      })

      document.getElementById("meal_search").addEventListener('change', function(e){
        orderEditor.filterReports("meal", e.target.value)
      })

      document.getElementById("start_date_search").addEventListener('change', function(e){
        orderEditor.filterReports("start_date", e.target.value)
      })

      document.getElementById("end_date_search").addEventListener('change', function(e){
        orderEditor.filterReports("end_date", e.target.value)
      })

      document.querySelector(".btn.prev").style.display = "none";
      document.querySelector(".btn.prev").addEventListener("click", function(){
        orderEditor.previousReportPage();
      })

      document.querySelector(".btn.next").addEventListener("click", function(){
        orderEditor.nextReportPage();
      })

      document.querySelector("#item_name").addEventListener("click", function(e){
        orderEditor.updateSort("name", function(){
          orderEditor.getReport().then(function(){
            document.querySelector(".reports").innerHTML = "";
            orderEditor.buildReports();
            e.target.className = orderEditor.sort.sortOrder
          });
        });
      })

      document.querySelector("#item_count").addEventListener("click", function(e){
        orderEditor.updateSort("count", function(){
          orderEditor.getReport().then(function(){
            document.querySelector(".reports").innerHTML = "";
            orderEditor.buildReports();
            e.target.className = orderEditor.sort.sortOrder
          });
        });
      })
    })
  </script>
</body>
</html>
