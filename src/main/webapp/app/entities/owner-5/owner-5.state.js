(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('owner-5', {
            parent: 'entity',
            url: '/owner-5?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner5S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-5/owner-5-s.html',
                    controller: 'Owner5Controller',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('owner-5-detail', {
            parent: 'owner-5',
            url: '/owner-5/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Owner5'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/owner-5/owner-5-detail.html',
                    controller: 'Owner5DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Owner5', function($stateParams, Owner5) {
                    return Owner5.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'owner-5',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('owner-5-detail.edit', {
            parent: 'owner-5-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-5/owner-5-dialog.html',
                    controller: 'Owner5DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner5', function(Owner5) {
                            return Owner5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-5.new', {
            parent: 'owner-5',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-5/owner-5-dialog.html',
                    controller: 'Owner5DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('owner-5', null, { reload: 'owner-5' });
                }, function() {
                    $state.go('owner-5');
                });
            }]
        })
        .state('owner-5.edit', {
            parent: 'owner-5',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-5/owner-5-dialog.html',
                    controller: 'Owner5DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Owner5', function(Owner5) {
                            return Owner5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-5', null, { reload: 'owner-5' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('owner-5.delete', {
            parent: 'owner-5',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/owner-5/owner-5-delete-dialog.html',
                    controller: 'Owner5DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Owner5', function(Owner5) {
                            return Owner5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('owner-5', null, { reload: 'owner-5' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
