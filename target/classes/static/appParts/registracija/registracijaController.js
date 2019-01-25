(function () {
    'use strict';

    angular
		.module('app')
		.controller('registracijaController', registracijaController);

    registracijaController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams', '$http'];
        function registracijaController( $scope, $window, $localStorage, $location, $stateParams, $http) {
            $scope.token = $stateParams.token;
            var processInstanceId = "";
            var taskId="";
            var init = function(){
            	$scope.korisnik={};
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/registracija/get/'+$window.localStorage.getItem('processInstanceId')
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
            
            $scope.registrujSe = function(){
            	var retVal = $scope.korisnik;
            	if(!retVal.lozinka){
            		$scope.secretMessage="Lozinka nije unesena";
            		return;
            	}
            	if(retVal.lozinka!=retVal.lozinka2){
            		$scope.secretMessage="Lozinke se ne poklapaju";
            		return;
            	}	
            	if(!retVal.ime){
            		$scope.secretMessage="Potrebno je uneti sve podatke";
            		return;
            	}
            	for(var i=0; i<$scope.formFields.length; i++){
            		var ff=$scope.formFields[i];
            		if(!ff.value.value){
                		$scope.secretMessage="Potrebno je uneti sve podatke";
                		return;
                	}
            	}
            	var retVal1 = {"formFields": $scope.formFields,
            			"processInstanceId": processInstanceId,
            			"taskId": taskId};
            	var retVal3 =  { "ime": retVal.ime,
            					"lozinka": retVal.lozinka
            	}
            	var retVal2 = {
            			"formFieldsCamunda": retVal1,
            			"korisnikDTO": retVal3
            	}
            	$http({
                    method: 'POST',
                    url: 'https://localhost:8087/NaucnaCentrala/registracija/do',
                    data: retVal2
                  }).then(function successCallback(response){
                	  if(response.data!=""){
                		  $window.localStorage.setItem('token', response.data); 
                		  var tokenData = jwtHelper.decodeToken($window.localStorage.getItem('token'));
                		  var tempUser = {id: tokenData.id, 
                				  korisnickoIme : tokenData.sub, 
                				  uloga : tokenData.uloga[0].authority,
                				  processId: processInstanceId
                		  		}
                		  $location.path('/home/'+tokenData.id);
                	  }          	  
                  },
                    function errorCallback(response){
                        alert("Greska");
                        $location.path('/login');
                    });
            }
            
           
        }
})();