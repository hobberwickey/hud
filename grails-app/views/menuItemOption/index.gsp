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
      <form class='item-option-form'>
        <ul class='sortable-lists groups'>
          
        </ul> 
      </form>
    </div>
  </div>
  <asset:javascript src='item-option-editor.js' />
  <script>
    $(function(){
      var itemOptionEditor = new ItemOptionEditor();
          itemOptionEditor.refreshOptions().then(function(){
            itemOptionEditor.buildOptionGroups()
          })
    })
  </script>
</body>
</html>
