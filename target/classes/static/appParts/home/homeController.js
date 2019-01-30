(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http'];
        function homeController( $scope, $window, $localStorage, $location, $stateParams, $http) {
        	var hc=this;
            $scope.token = $stateParams.token;

            $scope.init = function(){
            	if($stateParams.token=="")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	else{
            		$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/login/checkValidity/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data==false)
                      		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });
            	}
            }


        }
})();