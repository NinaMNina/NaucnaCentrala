(function () {
    'use strict';

    angular
		.module('app')
		.controller('loginController', loginController);

    loginController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', 'jwtHelper'];
        function loginController( $scope, $window, $localStorage, $location, $stateParams, $http, jwtHelper) {
            $scope.token = $stateParams.token;
            var processInstanceId = "";
            var taskId="";
            $scope.poruka="";
            var init = function(){
            	$scope.korisnik={};
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/login/get'
                  }).then(function successCallback(response){
                	  if(response.data!="")
                		  $scope.formFields = response.data.formFields;
                	  	  processInstanceId = response.data.processInstanceId;
                	  	  taskId = response.data.taskId;
                  },
                    function errorCallback(response){
                        alert("Greska");
                    });
            };
            init();
            
            $scope.ulogujSe = function(){
            	var retVal = $scope.korisnik;
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/login/do',
                    data: retVal
                  }).then(function successCallback(response){
                	  if(response.data.lozinka!=""){
                		  $window.localStorage.setItem('token', response.data.lozinka); 
                		  var tokenData = jwtHelper.decodeToken($window.localStorage.getItem('token'));
                		  var tempUser = {id: tokenData.id, 
                				  korisnickoIme : tokenData.sub, 
                				  uloga : tokenData.uloga[0].authority,
                				  processId: processInstanceId
                		  		}
                		  $location.path('/home');
                	  }         	  
                  },
                    function errorCallback(response){
                        $scope.poruka = "Pogrešno uneseni lozinka ili ime"
                    });
            }
            
            $scope.registrujSe = function(){
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/login/registracija/'+taskId
                  }).then(function successCallback(response){
                		 $location.path('/registracija');
                		 $window.localStorage.setItem('processInstanceId', processInstanceId); 
                  },
                    function errorCallback(response){
                        alert("Greska");
                    });
            	
            }
        }
})();