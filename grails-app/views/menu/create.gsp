<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Food Stuffs</title>

  <!-- <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous"> -->
</head>
<body>
  <div class='admin'>
    <div class='content'>
      <ul class='menus'>

      </ul> 
    </div>
  </div>
  <asset:javascript src='item-editor.js' />
  <script>
    $(function(){
      var itemEditor = new ItemEditor();

      itemEditor.refreshMenu(null).then(function(){
        itemEditor.buildMenuForm(itemEditor.menu);
      })
    })
  </script>
</body>
</html>
