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
            $scope.isCamundaTask=false;
            $scope.poruka="";
            var init = function(){
            	$scope.korisnik={};
            	var odabranCasopisId = $window.localStorage.getItem('odabranCasopisId'); 
            	if(odabranCasopisId==null || odabranCasopisId==undefined){
            		$scope.isCamundaTask=false;
            		return;
            	}
            	$http({
                    method: 'GET',
                    url: 'https://localhost:8087/NaucnaCentrala/login/get'
                  }).then(function successCallback(response){
                	  if(response.data!="" || response.data!=null || response.data!=undefined){
                		  $scope.formFields = response.data.formFields;
                	  	  processInstanceId = response.data.processInstanceId;
                	  	  taskId = response.data.taskId;
                		  $window.localStorage.setItem('taskId', taskId); 
                          $scope.isCamundaTask=true;
                	  }
                	  else{
                          $scope.isCamundaTask=false;
                	  		  
                	  }
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
                	  if(response.data==null || response.data==retVal || response.data==undefined || response.data==""){
                          $scope.poruka = "Pogrešno uneseni lozinka ili ime";           		  
                	  }
                	  else if(response.data.lozinka!="" || response.data.lozinka!=undefined){
                		  $window.localStorage.setItem('token', response.data.lozinka);            
                		  var c = $window.localStorage.getItem('odabranCasopisId');
	                      if(c==null || c==undefined){
	                		  $location.path('/home');
	                      }
	                      else{
	                    	  var taskId = $window.localStorage.getItem('taskId');    
	                    	  var odabranCasopisId = $window.localStorage.getItem('odabranCasopisId');
	                    	  if(taskId==undefined)
	                    		  taskId = "nemaga";
	                    	  $http({
	                              method: 'POST',
	                              url: 'https://localhost:8087/NaucnaCentrala/casopis/odaberiTask/'+taskId+'/'+odabranCasopisId+'/'+$window.localStorage.getItem('token')
	                            }).then(function successCallback(response){
	                          	  if(response.data!=null){
	                                	$window.location.href = 'https://localhost:8087/NaucnaCentrala/#!/zadaci/'+$window.localStorage.getItem('token');
	                          	  }
	                            },
	                              function errorCallback(response){
	                                  
	                             });
	                      }
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