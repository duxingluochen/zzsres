/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * String controller
 */

var KisBpmStringPropertyCtrl = [ '$scope', function ($scope) {

	$scope.shapeId = $scope.selectedShape.id;
	$scope.valueFlushed = false;
    /** Handler called when input field is blurred */
    $scope.inputBlurred = function() {
    	$scope.valueFlushed = true;
    	if ($scope.property.value) {
    		$scope.property.value = $scope.property.value.replace(/(<([^>]+)>)/ig,"");
    	}
        $scope.updatePropertyInModel($scope.property);
    };

    $scope.enterPressed = function(keyEvent) {
    	if (keyEvent && keyEvent.which === 13) {
    		keyEvent.preventDefault();
	        $scope.inputBlurred(); // we want to do the same as if the user would blur the input field
    	}
    };
    
    $scope.$on('$destroy', function controllerDestroyed() {
    	if(!$scope.valueFlushed) {
    		if ($scope.property.value) {
        		$scope.property.value = $scope.property.value.replace(/(<([^>]+)>)/ig,"");
        	}
    		$scope.updatePropertyInModel($scope.property, $scope.shapeId);
    	}
    });
     

}];




/**
 * 可编辑字段
 */
var KisBpmLinkEditPropertyCtrl = [ '$scope', '$modal', function($scope, $modal) {

    var opts = {
        template:  'editor-app/configuration/properties/link-editcoums.html?version=' + Date.now(),
        scope: $scope
    };

    $modal(opts);
}];
var lis={};
var KisBpmLinkEditcoumsPropertyPopupCtrl = ['$scope', '$http', function($scope, $http) {
	var ll={};
	//访问后台
	$scope.getRoles = function (successCallback) {
		    $http({    
		    	method: 'get',
		        headers: {'Accept': 'application/json',
		                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
		        	url:'view/findEntryDataById.do?id='+theRequest.ActivitiModelRelesId,
		        	})
		        .success(function (data, status, headers, config) {
		        	var obj = data.message;
		        	ll = JSON.parse(obj);
		        	for(var i=0;i<$scope.selectedItem.properties.length;i++){
		        		if($scope.selectedItem.properties[i].key=="oryx-editcoum"){
		        			var sss = $scope.selectedItem.properties[i].value;
		        			if(sss==""){$scope.lists=ll;break};
		        			var map = eval("("+sss+")");
		        			for ( var x in map) {
        	                	for ( var y in ll) {
        	                		if(x==ll[y].name)ll[y]["checked"]=true;
        						}
		        			}
		        			$scope.lists=ll;
		        		}
		        	}
		        })
		        .error(function (data, status, headers, config) {
		        });
	};
	$scope.getRoles(function(){});
	
	$scope.updateSelection = function (position) {
            if (lis[ll[position].name]==null) {
            	lis[ll[position].name]=ll[position].value;
            }else{
            	delete lis[ll[position].name]; 
            }
    };
	 $scope.save = function() {
	    	$scope.property.value = JSON.stringify(lis);
	        $scope.updatePropertyInModels($scope.property,1);
	        $scope.close();
	    };
	    $scope.close = function() {
	        $scope.property.mode = 'read';
	        $scope.$hide();
	    };
	    $scope.updatePropertyInModels=function(property,shapeId){
	    	if(shapeId){
	    		$scope.updatePropertyInModel($scope.property);
	    	}
	    	if(property.key=="oryx-role_name"){
	    		for(var i=0;i<$scope.selectedItem.properties.length;i++){
	    			if($scope.selectedItem.properties[i].key=="oryx-name"){
	    				$scope.selectedItem.properties[i].value=property.value;
	    				$scope.property.value=property.value;
	    				$scope.selectedItem.title=property.value;
	    				$scope.updatePropertyInModel($scope.property);
	    			}	    			
	    		}
	    	}
	    }
}];
/*
 * Boolean controller
 */

var KisBpmBooleanPropertyCtrl = ['$scope', function ($scope) {

    $scope.changeValue = function() {
        if ($scope.property.key === 'oryx-defaultflow' && $scope.property.value) {
            var selectedShape = $scope.selectedShape;
            if (selectedShape) {
                var incomingNodes = selectedShape.getIncomingShapes();
                if (incomingNodes && incomingNodes.length > 0) {
                    // get first node, since there can be only one for a sequence flow
                    var rootNode = incomingNodes[0];
                    var flows = rootNode.getOutgoingShapes();
                    if (flows && flows.length > 1) {
                        // in case there are more flows, check if another flow is already defined as default
                        for (var i = 0; i < flows.length; i++) {
                            if (flows[i].resourceId != selectedShape.resourceId) {
                                var defaultFlowProp = flows[i].properties['oryx-defaultflow'];
                                if (defaultFlowProp) {
                                    flows[i].setProperty('oryx-defaultflow', false, true);
                                }
                            }
                        }
                    }
                }
            }
        }
        $scope.updatePropertyInModel($scope.property);
    };

}];

/*
 * Text controller
 */

var KisBpmTextPropertyCtrl = [ '$scope', '$modal', function($scope, $modal) {

    var opts = {
        template:  'editor-app/configuration/properties/text-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
}];

var deptId = "";

var KisBpmTextPropertyPopupCtrl = ['$scope', '$translate', '$http', function($scope, $translate, $http) {
	var somss = [];
	
	
	//访问后台
	$scope.getAllRoles = function (successCallback) {
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        	url:'company/findComLists.do',
	        	})
	        .success(function (data, status, headers, config) {
	        	//debugger;
	        	var obj = data;
	        	somss=obj;
	        	$scope.coms = obj;
	        	$scope.property.depts = [];
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getAllRoles(function(){});
	 $scope.multiInstanceChanged = function() {
	    $scope.updatePropertyInModel($scope.property);
    };
    $scope.changecity = function(s1) {
    	$scope.depts=s1.depts;
    };
    $scope.changedept = function(s2) {
    };
    $scope.save = function() {
    	$scope.property.value = $scope.s2.deptId;
        $scope.updatePropertyInModel($scope.property);
        deptId = $scope.s2.deptId;
        $scope.close();
    };

    $scope.close = function() {
        $scope.property.mode = 'read';
        $scope.$hide();
    };
}];

var KisBpmLinkRolesPropertyCtrl = [ '$scope', '$modal', function($scope, $modal) {

    var opts = {
        template:  'editor-app/configuration/properties/link-popup.html?version=' + Date.now(),
        scope: $scope
    };

    $modal(opts);
}];

var KisBpmLinkPropertyPopupCtrl = ['$scope', '$http', function($scope, $http) {
	for(var i=0;i<$scope.selectedItem.properties.length;i++){
		if($scope.selectedItem.properties[i].key=="oryx-documentation"){
			deptId = $scope.selectedItem.properties[i].value;
		}
	}
	
	//访问后台
	$scope.getRoles = function (successCallback) {
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        	url:'role/findListRolesBydeptId.do?deptId='+ deptId,
	        	})
	        .success(function (data, status, headers, config) {
	        	var obj = data;
	        	$scope.roles = obj;
	        	
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getRoles(function(){});
	
	 $scope.save = function() {
	    	$scope.property.value = $scope.ss.roleName;
	        $scope.updatePropertyInModels($scope.property,1);
	        $scope.close();
	    };

	    $scope.close = function() {
	        $scope.property.mode = 'read';
	        $scope.$hide();
	    };
	    
	    $scope.updatePropertyInModels=function(property,shapeId){
	    	if(shapeId){
	    		$scope.updatePropertyInModel($scope.property);
	    	}
	    	if(property.key=="oryx-role_name"){
	    		for(var i=0;i<$scope.selectedItem.properties.length;i++){
	    			if($scope.selectedItem.properties[i].key=="oryx-name"){
	    				$scope.selectedItem.properties[i].value=property.value;
	    				$scope.property.value=property.value;
	    				//$scope.updatePropertyInModel($scope.selectedItem.properties[i]);
	    				$scope.selectedItem.title=property.value;
	    				$scope.updatePropertyInModel($scope.property);
	    				
	    			}	    			
	    		}
	    	}
	    	
	    }
	
}];
