import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerAccount } from 'app/shared/model/partner-account.model';
import { PartnerAccountService } from './partner-account.service';

@Component({
    selector: 'jhi-partner-account-delete-dialog',
    templateUrl: './partner-account-delete-dialog.component.html'
})
export class PartnerAccountDeleteDialogComponent {
    partnerAccount: IPartnerAccount;

    constructor(
        private partnerAccountService: PartnerAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.partnerAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'partnerAccountListModification',
                content: 'Deleted an partnerAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-partner-account-delete-popup',
    template: ''
})
export class PartnerAccountDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartnerAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.partnerAccount = partnerAccount;
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
