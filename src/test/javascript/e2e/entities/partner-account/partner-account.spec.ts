/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerAccountComponentsPage, PartnerAccountDeleteDialog, PartnerAccountUpdatePage } from './partner-account.page-object';

const expect = chai.expect;

describe('PartnerAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerAccountUpdatePage: PartnerAccountUpdatePage;
    let partnerAccountComponentsPage: PartnerAccountComponentsPage;
    let partnerAccountDeleteDialog: PartnerAccountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PartnerAccounts', async () => {
        await navBarPage.goToEntity('partner-account');
        partnerAccountComponentsPage = new PartnerAccountComponentsPage();
        expect(await partnerAccountComponentsPage.getTitle()).to.eq('pinguinApp.partnerAccount.home.title');
    });

    it('should load create PartnerAccount page', async () => {
        await partnerAccountComponentsPage.clickOnCreateButton();
        partnerAccountUpdatePage = new PartnerAccountUpdatePage();
        expect(await partnerAccountUpdatePage.getPageTitle()).to.eq('pinguinApp.partnerAccount.home.createOrEditLabel');
        await partnerAccountUpdatePage.cancel();
    });

    it('should create and save PartnerAccounts', async () => {
        const nbButtonsBeforeCreate = await partnerAccountComponentsPage.countDeleteButtons();

        await partnerAccountComponentsPage.clickOnCreateButton();
        await promise.all([
            partnerAccountUpdatePage.setAccountNameInput('accountName'),
            partnerAccountUpdatePage.partnerSelectLastOption(),
            partnerAccountUpdatePage.partnerUserSelectLastOption()
        ]);
        expect(await partnerAccountUpdatePage.getAccountNameInput()).to.eq('accountName');
        await partnerAccountUpdatePage.save();
        expect(await partnerAccountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PartnerAccount', async () => {
        const nbButtonsBeforeDelete = await partnerAccountComponentsPage.countDeleteButtons();
        await partnerAccountComponentsPage.clickOnLastDeleteButton();

        partnerAccountDeleteDialog = new PartnerAccountDeleteDialog();
        expect(await partnerAccountDeleteDialog.getDialogTitle()).to.eq('pinguinApp.partnerAccount.delete.question');
        await partnerAccountDeleteDialog.clickOnConfirmButton();

        expect(await partnerAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
