/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerComponentsPage, PartnerDeleteDialog, PartnerUpdatePage } from './partner.page-object';

const expect = chai.expect;

describe('Partner e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerUpdatePage: PartnerUpdatePage;
    let partnerComponentsPage: PartnerComponentsPage;
    let partnerDeleteDialog: PartnerDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Partners', async () => {
        await navBarPage.goToEntity('partner');
        partnerComponentsPage = new PartnerComponentsPage();
        expect(await partnerComponentsPage.getTitle()).to.eq('pinguinApp.partner.home.title');
    });

    it('should load create Partner page', async () => {
        await partnerComponentsPage.clickOnCreateButton();
        partnerUpdatePage = new PartnerUpdatePage();
        expect(await partnerUpdatePage.getPageTitle()).to.eq('pinguinApp.partner.home.createOrEditLabel');
        await partnerUpdatePage.cancel();
    });

    it('should create and save Partners', async () => {
        const nbButtonsBeforeCreate = await partnerComponentsPage.countDeleteButtons();

        await partnerComponentsPage.clickOnCreateButton();
        await promise.all([partnerUpdatePage.setPartnerNameInput('partnerName'), partnerUpdatePage.partnerTypeSelectLastOption()]);
        expect(await partnerUpdatePage.getPartnerNameInput()).to.eq('partnerName');
        await partnerUpdatePage.save();
        expect(await partnerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Partner', async () => {
        const nbButtonsBeforeDelete = await partnerComponentsPage.countDeleteButtons();
        await partnerComponentsPage.clickOnLastDeleteButton();

        partnerDeleteDialog = new PartnerDeleteDialog();
        expect(await partnerDeleteDialog.getDialogTitle()).to.eq('pinguinApp.partner.delete.question');
        await partnerDeleteDialog.clickOnConfirmButton();

        expect(await partnerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
