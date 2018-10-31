import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerRole } from 'app/shared/model/partner-role.model';
import { PartnerRoleService } from './partner-role.service';

@Component({
    selector: 'jhi-partner-role-delete-dialog',
    templateUrl: './partner-role-delete-dialog.component.html'
})
export class PartnerRoleDeleteDialogComponent {
    partnerRole: IPartnerRole;

    constructor(
        private partnerRoleService: PartnerRoleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.partnerRoleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'partnerRoleListModification',
                content: 'Deleted an partnerRole'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-partner-role-delete-popup',
    template: ''
})
export class PartnerRoleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerRole }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartnerRoleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.partnerRole = partnerRole;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
