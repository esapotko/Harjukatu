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
     * Content
     */
    $scope.myInterval = 3000;
    $scope.slides = [
        {
          image: 'http://lorempixel.com/400/200/'
        },
        {
          image: 'http://lorempixel.com/400/200/food'
        },
        {
          image: 'http://lorempixel.com/400/200/sports'
        },
        {
          image: 'http://lorempixel.com/400/200/people'
        }
    ];    
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
