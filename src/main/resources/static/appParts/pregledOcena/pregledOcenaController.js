(function () {
    'use strict';

    angular
		.module('app')
		.controller('pregledOcenaController', pregledOcenaController);

    pregledOcenaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http', '$sce'];
        function pregledOcenaController( $scope, $window, $localStorage, $location, $stateParams, $http, $sce) {
            $scope.token = $stateParams.token;
        	$scope.porukica = "";
        	$scope.secretMessage = "";
        	$scope.rok={"dana": 7,"minuta": 0, "sati": 0};
            $scope.init = function(){
            	if($stateParams.token=="" || $stateParams.token=="{token}" || $stateParams.token==undefined || $stateParams.token==null || $stateParams.token=="null")
            		$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            	$http({
	        		method: 'GET',
	                url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledOcena/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')
	        	}).then(function successCallback(response){
	        		$scope.ocene = response.data;
                },
                  function errorCallback(response){
                  });
            }
            $scope.home = function(){
            	var token = $window.localStorage.getItem('token');
            	$location.path('/home');
            }                     
            $scope.odjaviSe = function(){
            	$stateParams.token = "";
            	$window.localStorage.clear();
            	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/login';
            }

            $scope.zavrsi = function(){
        		var rok = "P";
        		if($scope.ocenica==undefined || $scope.ocenica==null){
        			alert("Molimo, unesite svoju ocenu");
            		return;
        		}
            	if($scope.ocenica=="MANJE_IZMENE" || $scope.ocenica=="VECE_IZMENE"){
            		if(!($scope.rok.dana>0 || $scope.rok.sati>0 || $scope.rok.minuta>0)){
                		alert("Niste uneli rok za recenziju");
                		return;
                	}
                	if($scope.rok.dana>0){
                		rok=rok+$scope.rok.dana.toString()+"D";
                	}
                	if($scope.rok.sati>0){
                		rok=rok+"T"+$scope.rok.sati+"H";
                	}
                	if($scope.rok.minuta>0){
                		if($scope.rok.sati<1)
                			rok+='T'
                		rok=rok+$scope.rok.minuta+"M";
                	}
            	}    	                   	
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/zadaci/pregledOcena/'+$window.localStorage.getItem('taskIdOdZadaciObrisiOdmah')+'/'+$scope.ocenica+'/'+rok
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                    	$window.localStorage.removeItem('taskIdOdZadaciObrisiOdmah');
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home'             			          		  
                	  }
                  },
                    function errorCallback(response){
                        alert("Greška pri obavljanu zadatka");
                    	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/home';
                    });     
            }
        
        }
})();