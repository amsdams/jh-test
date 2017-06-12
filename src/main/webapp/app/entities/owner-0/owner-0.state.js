(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-0', {
            parent: 'entity',
            url: '/owner-0',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner0S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-0/owner-0-s.html',
                    controller: 'Owner0Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('owner-0-detail', {
            parent: 'owner-0',
            url: '/owner-0/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner0'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-0/owner-0-detail.html',
                    controller: 'Owner0DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner0', function($stateParams, Owner0) {
                    return Owner0.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-0',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-0-detail.edit', {
            parent: 'owner-0-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-0/owner-0-dialog.html',
                    controller: 'Owner0DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner0', function(Owner0) {
                            return Owner0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-0.new', {
            parent: 'owner-0',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-0/owner-0-dialog.html',
                    controller: 'Owner0DialogController',
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
                    $state.go('owner-0', null, { reload: 'owner-0' });
                }, function() {
                    $state.go('owner-0');
                });
            }]
        })
        .state('owner-0.edit', {
            parent: 'owner-0',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-0/owner-0-dialog.html',
                    controller: 'Owner0DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner0', function(Owner0) {
                            return Owner0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-0', null, { reload: 'owner-0' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-0.delete', {
            parent: 'owner-0',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-0/owner-0-delete-dialog.html',
                    controller: 'Owner0DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner0', function(Owner0) {
                            return Owner0.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-0', null, { reload: 'owner-0' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
