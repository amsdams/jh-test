(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-0', {
            parent: 'entity',
            url: '/car-0',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car0S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-0/car-0-s.html',
                    controller: 'Car0Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('car-0-detail', {
            parent: 'car-0',
            url: '/car-0/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car0'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-0/car-0-detail.html',
                    controller: 'Car0DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Car0', function($stateParams, Car0) {
                    return Car0.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-0',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-0-detail.edit', {
            parent: 'car-0-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-0/car-0-dialog.html',
                    controller: 'Car0DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car0', function(Car0) {
                            return Car0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-0.new', {
            parent: 'car-0',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-0/car-0-dialog.html',
                    controller: 'Car0DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-0', null, { reload: 'car-0' });
                }, function() {
                    $state.go('car-0');
                });
            }]
        })
        .state('car-0.edit', {
            parent: 'car-0',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-0/car-0-dialog.html',
                    controller: 'Car0DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car0', function(Car0) {
                            return Car0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-0', null, { reload: 'car-0' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-0.delete', {
            parent: 'car-0',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-0/car-0-delete-dialog.html',
                    controller: 'Car0DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car0', function(Car0) {
                            return Car0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-0', null, { reload: 'car-0' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
