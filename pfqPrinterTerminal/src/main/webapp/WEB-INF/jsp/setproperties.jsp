<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="printerTerminalApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Spring Security Example</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
	integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi"
	crossorigin="anonymous">
<style type="text/css">
.box.box-info {
	border-top-color: #00c0ef;
}

.box {
	position: relative;
	border-radius: 3px;
	background: #ffffff;
	border-top: 3px solid #d2d6de;
	margin-bottom: 20px;
	width: 100%;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.box-header.with-border {
	border-bottom: 1px solid #f4f4f4;
}

.box-header {
	color: #444;
	display: block;
	padding: 10px;
	position: relative;
}

.box-body {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
	border-bottom-right-radius: 3px;
	border-bottom-left-radius: 3px;
	padding: 10px;
}

.btn-app {
	border-radius: 3px;
	position: relative;
	padding: 15px 5px;
	margin: 0 0 10px 10px;
	min-width: 80px;
	height: 60px;
	text-align: center;
	color: #666;
	border: 1px solid #ddd;
	background-color: #f4f4f4;
	font-size: 12px;
}

.btn {
	border-radius: 3px;
	-webkit-box-shadow: none;
	box-shadow: none;
	border: 1px solid transparent;
}

.btn.btn-flat {
	border-radius: 0;
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
	border-width: 1px;
}

.form-control {
	border-radius: 0;
	box-shadow: none;
	border-color: #d2d6de;
}

.box-footer {
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
    border-top: 1px solid #f4f4f4;
    padding: 10px;
    background-color: #fff;
}


</style>
</head>
<body>

	<div class="container-fluid" ng-controller="mainController">
	<br>
		<div class="row">
			<div class="col-md-8">
				<form>
					<div class="box box-info">
						<div class="box-header">
							<h3 class="box-title">Configuration</h3>
						</div>
						<div class="box-body">

							<div class="row">
								<div class="col-md-8">
									<label for="exampleInputTerminalGUID">Terminal GUID</label>
									<div class="input-group">
										<input type="text" class="form-control"
											ng-model="Details.GUID" id="exampleInputTerminalGUID"
											placeholder="Generate Terminal GUID"> <span
											class="input-group-btn">
											<button type="button" class="btn btn-primary"
												ng-click="generateGuid()">Generate!</button>
										</span>
									</div>


								</div>
								<div class="col-md-4">

									<div class="form-group">
										<label for="exampleInputTerminalName">Terminal Name</label> <input
											type="text" class="form-control"
											id="exampleInputTerminalName"
											placeholder="Enter Terminal name" ng-model="Details.NAME">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputAddress">Server address</label> <input
											type="text" class="form-control" id="exampleInputAddress"
											placeholder="Enter Server address" ng-model="Details.SERVER">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputApp">Server App</label> <input
											type="text" class="form-control" id="exampleInputApp"
											placeholder="Enter Server address"
											ng-model="Details.SERVERAPP">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputPort">Server port</label> <input
											type="text" class="form-control" id="exampleInputPort"
											placeholder="Enter Server port" ng-model="Details.PORT">
									</div>
								</div>
							</div>



							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputTerminalIdSklad">ID Sklad</label> <input
											type="text" class="form-control"
											id="exampleInputTerminalIdSklad"
											placeholder="Enter Terminal name" ng-model="Details.IDSKLAD">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputTerminalNameSklad">Name Sklad</label>
										<input type="text" class="form-control"
											id="exampleInputTerminalNameSklad"
											placeholder="Enter Terminal name" ng-model="Details.NAMESLAD">
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-4">

									<div class="form-group">
										<label for="exampleInputLogin">Login</label> <input
											type="text" class="form-control" id="exampleInputLogin"
											placeholder="Enter Login" ng-model="Details.LOGIN">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="exampleInputPassword1">Password</label> <input
											type="password" class="form-control"
											id="exampleInputPassword1" placeholder="Password"
											ng-model="Details.PASSWORD">
									</div>
								</div>
							</div>






						</div>
						<div class="box-footer">
							<button type="submit" class="btn btn-info pull-right btn-flat"
								ng-click="saveValues()">Save</button>
						</div>

					</div>
				</form>
			</div>
			<div class="col-md-4">
				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Status Server</h3>
					</div>
					<div class="box-body">


						<label for="exampleInputTerminalStatus">Status</label>
						<div class="input-group">
							<input type="text" class="form-control" disabled="" ng-model="Details.STATUS" id="exampleInputTerminalStatus"> 
								<span class="input-group-btn">
								   <button type="button" class="btn btn-info btn-flat" ng-click="Start()" ng-if="Details.STATUS != 'STARTED'" >Connect</button>
								   <button type="button" class="btn btn-info btn-flat" ng-click="Stop()"  ng-if="Details.STATUS == 'STARTED'">Disconnect</button>
							    </span>
						</div>
			
					</div>
				</div>


				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Info</h3>
					</div>
					<div class="box-body">

						<table class="table table-hover">
							<tbody id="price"></tbody>
						</table>
					</div>

				</div>
			</div>

		</div>




	</div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
		integrity="sha384-3ceskX3iaEnIogmQchP8opvBy3Mi7Ce34nWjpBIwVTHfGYWQS9jwHDVRnpKKHJg7"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.7/js/tether.min.js"
		integrity="sha384-XTs3FgkjiBgo8qjEjBk0tGmf3wPrWtA6coPfQDfFEY8AnYJwjalXCiosYRBIBZX8"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
		integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
		crossorigin="anonymous"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>

	<script src="../res/app/sockjs.js"></script>
	<script src="../res/app/stomp.js"></script>

	<script type="text/javascript">
		var myApp = angular.module('printerTerminalApp', []);

		myApp
				.controller(
						'mainController',
						[
								'$scope',
								'$http',
								function($scope, $http) {

									$scope.Details = {};

									var update = function(){
										$http
										.get('/properties')
										.success(
												function(data, status,
														headers, config) {
													$scope.Details = data.Result;
												})
										.error(
												function(data, status,
														header, config) {
													$scope.ResponseDetails = "Data: "
															+ data
															+ "<br />status: "
															+ status
															+ "<br />headers: "
															+ jsonFilter(header)
															+ "<br />config: "
															+ jsonFilter(config);
												});
										};
									$scope.Start = function() {
										var data = JSON.stringify({});
										$http
										.post("/connet", data)
										.success(
												function(data, status) {
													update();
												});
							              
									};

									$scope.Stop = function() {

									};

									$scope.generateGuid = function() {
										$scope.Details.GUID = generateUUID();
									};

									$scope.saveValues = function() {

										var data = JSON
												.stringify({
													nameTerminal   : $scope.Details.NAME,
													guidTerminal   : $scope.Details.GUID,
													serverAddress  : $scope.Details.SERVER,
													serverApp      : $scope.Details.SERVERAPP,
													serverPort     : $scope.Details.PORT,
													idSklad        : $scope.Details.IDSKLAD,
													nameSklad      : $scope.Details.NAMESLAD,
													login          : $scope.Details.LOGIN,
													password       : $scope.Details.PASSWORD
												});

										$http
												.post("/set-properties", data)
												.success(
														function(data, status) {
															update();
														});
									};

									function generateUUID() {
										var d = new Date().getTime();
										if (window.performance
												&& typeof window.performance.now === "function") {
											d += performance.now();
										}
										var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
												.replace(
														/[xy]/g,
														function(c) {
															var r = (d + Math
																	.random() * 16) % 16 | 0;
															d = Math
																	.floor(d / 16);
															return (c == 'x' ? r
																	: (r & 0x3 | 0x8))
																	.toString(16);
														});
										return uuid;
									}

									update();

								} ]);
	</script>
	
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
            $('<td>').html(JSON.stringify(price.data))
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