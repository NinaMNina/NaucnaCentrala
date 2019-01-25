(function () {
    'use strict';

    angular
		.module('app')
		.controller('loginController', loginController);

    loginController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http'];
        function loginController( $scope, $window, $localStorage, $location, $stateParams, $http) {
            $scope.token = $stateParams.token;

            var init = function(){
            	$scope.korisnik={};
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/login/get'
                  }).then(function successCallback(response){
                	  if(response.data!="")
                		  $scope.formFields = response.data.formFields;
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
                	  if(response.data!=""){
                		  $window.localStorage.setItem('token', response.data); 
                		  var tokenData = jwtHelper.decodeToken($window.localStorage.getItem('token'));
                		  var tempUser = {id: tokenData.id, korisnickoIme : tokenData.sub, uloga : tokenData.uloga[0].authority}
                		  $location.path('/home/'+tokenData.id);
                	  }          	  
                  },
                    function errorCallback(response){
                        alert("Greska");
                    });
            }
            
            $scope.registrujSe = function(){
            	
            }
        }
})();