<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/app/bootstrap.css">
<title>Printer Ticker</title>
</head>
<body>

<div class="container-fluid">
  <h2>Printer Ticker</h2>
 
  <table class="table table-hover">
    <thead>
      <tr>
        <th>GUID</th>
        <th>NAME</th>
        <th>WORK</th>
        <th>DATA</th>
        <th>Time</th>
      </tr>
    </thead>
    <tbody id="price"></tbody>
  </table>
</div>


  <script src="resources/app/sockjs.js"></script>
  <script src="resources/app/stomp.js"></script>
  <script src="resources/app/jquery.js"></script>
  <script src="resources/app/bootstrap.js"></script>
  <script>
    //Create stomp client over sockJS protocol
    var socket = new SockJS("<%=request.getContextPath()%>/ws");
    var stompClient = Stomp.over(socket);

    // Render price data from server into HTML, registered as callback
    // when subscribing to price topic
    function renderPrice(frame) {
      var prices = JSON.parse(frame.body);
      $('#price').empty();
      for(var i in prices) {
        var price = prices[i];
        $('#price').append(
          $('<tr>').append(
            $('<td>').html(price.guid),
            $('<td>').html(price.name),
            $('<td>').html(price.work),
            $('<td>').html(JSON.stringify(price.data)),
            $('<td>').html(price.time)
          )
        );
      }
    }
    
    // Callback function to be called when stomp client is connected to server
    var connectCallback = function() {
      stompClient.subscribe('/topic/price', renderPrice);
    }; 

    // Callback function to be called when stomp client could not connect to server
    var errorCallback = function(error) {
      alert(error.headers.message);
    };

    // Connect to server via websocket
    stompClient.connect("guest", "guest", connectCallback, errorCallback);
    
  </script>

</body>
</html>