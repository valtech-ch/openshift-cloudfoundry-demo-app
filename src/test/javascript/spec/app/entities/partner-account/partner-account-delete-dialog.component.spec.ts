/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerAccountDeleteDialogComponent } from 'app/entities/partner-account/partner-account-delete-dialog.component';
import { PartnerAccountService } from 'app/entities/partner-account/partner-account.service';

describe('Component Tests', () => {
    describe('PartnerAccount Management Delete Component', () => {
        let comp: PartnerAccountDeleteDialogComponent;
        let fixture: ComponentFixture<PartnerAccountDeleteDialogComponent>;
        let service: PartnerAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerAccountDeleteDialogComponent]
            })
                .overrideTemplate(PartnerAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerAccountService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
