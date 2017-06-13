(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-5', {
            parent: 'entity',
            url: '/car-5?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car5S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-5/car-5-s.html',
                    controller: 'Car5Controller',
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
        .state('car-5-detail', {
            parent: 'car-5',
            url: '/car-5/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Car5'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-5/car-5-detail.html',
                    controller: 'Car5DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Car5', function($stateParams, Car5) {
                    return Car5.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-5',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-5-detail.edit', {
            parent: 'car-5-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-5/car-5-dialog.html',
                    controller: 'Car5DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car5', function(Car5) {
                            return Car5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-5.new', {
            parent: 'car-5',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-5/car-5-dialog.html',
                    controller: 'Car5DialogController',
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
                    $state.go('car-5', null, { reload: 'car-5' });
                }, function() {
                    $state.go('car-5');
                });
            }]
        })
        .state('car-5.edit', {
            parent: 'car-5',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-5/car-5-dialog.html',
                    controller: 'Car5DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car5', function(Car5) {
                            return Car5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-5', null, { reload: 'car-5' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-5.delete', {
            parent: 'car-5',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-5/car-5-delete-dialog.html',
                    controller: 'Car5DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car5', function(Car5) {
                            return Car5.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-5', null, { reload: 'car-5' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
