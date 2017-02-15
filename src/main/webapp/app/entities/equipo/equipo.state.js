(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('equipo', {
            parent: 'entity',
            url: '/equipo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.equipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equipo/equipos.html',
                    controller: 'EquipoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('equipo-detail', {
            parent: 'entity',
            url: '/equipo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.equipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equipo/equipo-detail.html',
                    controller: 'EquipoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equipo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Equipo', function($stateParams, Equipo) {
                    return Equipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'equipo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('equipo-detail.edit', {
            parent: 'equipo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipo/equipo-dialog.html',
                    controller: 'EquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equipo', function(Equipo) {
                            return Equipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equipo.new', {
            parent: 'equipo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipo/equipo-dialog.html',
                    controller: 'EquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                fechaCreacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('equipo', null, { reload: 'equipo' });
                }, function() {
                    $state.go('equipo');
                });
            }]
        })
        .state('equipo.edit', {
            parent: 'equipo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipo/equipo-dialog.html',
                    controller: 'EquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Equipo', function(Equipo) {
                            return Equipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equipo', null, { reload: 'equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('equipo.delete', {
            parent: 'equipo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/equipo/equipo-delete-dialog.html',
                    controller: 'EquipoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Equipo', function(Equipo) {
                            return Equipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('equipo', null, { reload: 'equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
