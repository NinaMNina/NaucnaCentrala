(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http'];
        function homeController( $scope, $window, $localStorage, $location, $stateParams, $http) {
        	var hc=this;
            $scope.token = $stateParams.token;
            $scope.params = [];
            $scope.pretragaReci = "";

            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	else{
            	/*	$http({
                        method: 'GET',
                        url: 'https://localhost:8087/NaucnaCentrala/login/checkValidity/'+$stateParams.token
                      }).then(function successCallback(response){
                    	  if(response.data==false)
                      		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
                      },
                        function errorCallback(response){
                            alert("Greska u zahtevu");
                        });*/
            	}
            }
            $scope.dodajParametar = function(){
                $scope.params.push({"operacija": "",
                	"polje": "",
                	"vrednost": ""});
            }
            
            $scope.pretrazi = function(){
            	var data = $scope.params;
            }
            $scope.obrisiParametar = function(parametar){
            	if($scope.params.length>=parametar)
            		$scope.params.splice(parametar, 1);
            }
            
            $scope.pretraziPoRecima = function(){
            	var data = $scope.pretragaReci;
            	if(data=="")
            		return;
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/rest/search/'+data
                  }).then(function successCallback(response){
                	  alert("dobio odgovor na pretragu")
                  },
                    function errorCallback(response){
                        alert("Greska u zahtevu za pretragu");
                    });
            }

        }
})();