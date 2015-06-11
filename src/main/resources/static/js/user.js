var app = angular.module('test_user', ['ngRoute']);

	app
	.controller('UserController', function($scope, $http, $window){
		$http.get('/user').success(function(data){
			$scope.users = data;
		})
		.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});
		$scope.deleteUser = function(user){
			var res = $http.delete("/user/" + user.id);
			 res.success(function(data, status, headers, config) {
			    	$window.location.reload();
			    });
			res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
		}
		$scope.addUser = function(){
			var res = $http.post("/user", $scope.newUser);
			 res.success(function(data, status, headers, config) {
			    	$window.location.reload();
			    });
			res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
		}
	})
