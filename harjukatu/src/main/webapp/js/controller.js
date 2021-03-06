var HarjukatuApp = angular.module('HarjukatuApp', ["ui.bootstrap"]);
// Main controller
HarjukatuApp.controller('HarjukatuCtrl', function($scope, $http) {
    /*
     * Menu
     */
    $scope.showModal = true;
    $http.get("/api/menu")
    .then(function(response) {
        $scope.menu = response.data; 
    })
    .catch(function (data) {
        console.log('get menu error',data)
    });
    /*
     * ToDo content
     */
    $http.get("/api/posts/0")
    .then(function(response) {
        $scope.posts = response.data; 
    })
    .catch(function (data) {
        console.log('get posts error',data)
    });
    $http.get("/api/posts/1")
    .then(function(response) {
        $scope.newposts = response.data; 
    })
    .catch(function (data) {
        console.log('get posts error',data)
    });
    $http.get("/api/posts/2")
    .then(function(response) {
        $scope.addedposts = response.data; 
    })
    .catch(function (data) {
        console.log('get posts error',data)
    });
    /*
     * Content, need a littebit nginx configuration and metada to be implemented
     */
    $scope.slideInterval = 3000;
    $http.get("/api/slides")
    .then(function(response) {
        $scope.slides = response.data; 
    })
    .catch(function (data) {
        console.log('get slides error',data)
    });
    /*
     * Functionality
     */
    $scope.removeItem = function (idx) {
        var url = "/api/posts/"+idx+"/del";
        $http.get(url)
        .then(function(response) {
            $scope.addedposts = response.data; 
        })
        .catch(function (data) {
            console.log('del posts error',url)
        });
    }
 
    $scope.addFile = function() {
        // Get form
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/upload",
            data: data,
            //http://api.jquery.com/jQuery.ajax/
            //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (response) {
                $scope.poststatus = response.type;
                $scope.message = response.message;
                console.log('status',$scope.poststatus);
                $("#message").text($scope.message);
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
       });
    };
});

// Create another angular controller
HarjukatuApp.controller('PostCtrl', function($scope, $http) {
    /*
     * Menu
     */
    $scope.showModal = true;
    $http.get('/api/menu')
    .then(function(response) {
        $scope.menu = response.data; 
    })
    .catch(function (data) {
        console.log('get menu error',data);
    });
    /*
     * Functionality
     */
    $scope.addPost = function() {

        console.log('scope.formError =',$scope.formError);
        
        $http.post('/api/add',$scope.post)
        .then(function(response) {
            $scope.poststatus = response.data.type;
            console.log('status',$scope.poststatus);
            
            if($scope.poststatus == "OK") {
                $scope.message = response.data.message;
            } else {
                $scope.message = "Error happened";
            }
    
        })
        .catch(function (data) {
            console.log('post error',data);
        });
    };
});
