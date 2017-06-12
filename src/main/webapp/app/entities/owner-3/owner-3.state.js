(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-3', {
            parent: 'entity',
            url: '/owner-3',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner3S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-3/owner-3-s.html',
                    controller: 'Owner3Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('owner-3-detail', {
            parent: 'owner-3',
            url: '/owner-3/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner3'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-3/owner-3-detail.html',
                    controller: 'Owner3DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner3', function($stateParams, Owner3) {
                    return Owner3.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-3',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-3-detail.edit', {
            parent: 'owner-3-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-3/owner-3-dialog.html',
                    controller: 'Owner3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner3', function(Owner3) {
                            return Owner3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-3.new', {
            parent: 'owner-3',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-3/owner-3-dialog.html',
                    controller: 'Owner3DialogController',
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
                    $state.go('owner-3', null, { reload: 'owner-3' });
                }, function() {
                    $state.go('owner-3');
                });
            }]
        })
        .state('owner-3.edit', {
            parent: 'owner-3',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-3/owner-3-dialog.html',
                    controller: 'Owner3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner3', function(Owner3) {
                            return Owner3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-3', null, { reload: 'owner-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-3.delete', {
            parent: 'owner-3',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-3/owner-3-delete-dialog.html',
                    controller: 'Owner3DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner3', function(Owner3) {
                            return Owner3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-3', null, { reload: 'owner-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
