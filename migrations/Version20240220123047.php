<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240220123047 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE account (accountId INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, surname VARCHAR(255) NOT NULL, gender VARCHAR(255) NOT NULL, phone_number INT NOT NULL, address VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(255) NOT NULL, PRIMARY KEY(accountId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE adoption (adoptionId INT AUTO_INCREMENT NOT NULL, adoption_date DATE NOT NULL, adoption_status VARCHAR(255) NOT NULL, adoption_fee DOUBLE PRECISION NOT NULL, Account_Key INT NOT NULL, INDEX IDX_EDDEB6A974BA3BC0 (Account_Key), PRIMARY KEY(adoptionId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE animal (animalId INT AUTO_INCREMENT NOT NULL, animal_name VARCHAR(255) NOT NULL, animal_breed VARCHAR(255) NOT NULL, animal_status VARCHAR(255) NOT NULL, animal_type VARCHAR(255) NOT NULL, age INT NOT NULL, enrollement_date DATE NOT NULL, PRIMARY KEY(animalId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE appointment (appointmentId INT AUTO_INCREMENT NOT NULL, appointment_date DATE NOT NULL, appointment_time TIME NOT NULL, appointment_status VARCHAR(255) NOT NULL, Account_Key INT NOT NULL, Animal_Key INT NOT NULL, INDEX IDX_FE38F84474BA3BC0 (Account_Key), INDEX IDX_FE38F844620EA577 (Animal_Key), PRIMARY KEY(appointmentId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE boarding (boardingId INT AUTO_INCREMENT NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, boarding_status VARCHAR(255) NOT NULL, boarding_fee DOUBLE PRECISION NOT NULL, Animal_Key INT NOT NULL, Account_Key INT NOT NULL, INDEX IDX_114209DA620EA577 (Animal_Key), INDEX IDX_114209DA74BA3BC0 (Account_Key), PRIMARY KEY(boardingId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE cash_register (cashRegisterId INT AUTO_INCREMENT NOT NULL, balance DOUBLE PRECISION NOT NULL, expenses DOUBLE PRECISION NOT NULL, donation_total DOUBLE PRECISION NOT NULL, adoption_fee_total DOUBLE PRECISION NOT NULL, boarding_fee_total DOUBLE PRECISION NOT NULL, PRIMARY KEY(cashRegisterId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE category (categoryId INT AUTO_INCREMENT NOT NULL, product_type VARCHAR(255) NOT NULL, product_source VARCHAR(255) NOT NULL, PRIMARY KEY(categoryId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation_m (donationMId INT AUTO_INCREMENT NOT NULL, donation_amount DOUBLE PRECISION NOT NULL, donation_m_date DATE NOT NULL, Account_Key INT NOT NULL, INDEX IDX_7B41703B74BA3BC0 (Account_Key), PRIMARY KEY(donationMId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation_p (donationPId INT AUTO_INCREMENT NOT NULL, donation_product_name VARCHAR(255) NOT NULL, donation_product_quantity INT NOT NULL, donation_product_label VARCHAR(255) NOT NULL, donation_product_expiration_date DATE NOT NULL, donation_p_date DATE NOT NULL, donation_p_type VARCHAR(255) NOT NULL, Account_Key INT NOT NULL, INDEX IDX_18471CE274BA3BC0 (Account_Key), PRIMARY KEY(donationPId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation_product (dpId INT AUTO_INCREMENT NOT NULL, Donation_Key INT NOT NULL, Product_Key INT NOT NULL, INDEX IDX_F9E6F81D38CC406A (Donation_Key), INDEX IDX_F9E6F81DD46E3681 (Product_Key), PRIMARY KEY(dpId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE product (productId INT AUTO_INCREMENT NOT NULL, product_name VARCHAR(255) NOT NULL, product_quantity INT NOT NULL, product_label VARCHAR(255) NOT NULL, expiration_date DATE NOT NULL, Category_Key INT NOT NULL, INDEX IDX_D34A04AD8F2E9BEB (Category_Key), PRIMARY KEY(productId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE rapport (rapportId INT AUTO_INCREMENT NOT NULL, description VARCHAR(1000) NOT NULL, Appointment_Key INT NOT NULL, UNIQUE INDEX UNIQ_BE34A09C954844CF (Appointment_Key), PRIMARY KEY(rapportId)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE adoption ADD CONSTRAINT FK_EDDEB6A974BA3BC0 FOREIGN KEY (Account_Key) REFERENCES account (accountId)');
        $this->addSql('ALTER TABLE appointment ADD CONSTRAINT FK_FE38F84474BA3BC0 FOREIGN KEY (Account_Key) REFERENCES account (accountId)');
        $this->addSql('ALTER TABLE appointment ADD CONSTRAINT FK_FE38F844620EA577 FOREIGN KEY (Animal_Key) REFERENCES animal (animalId)');
        $this->addSql('ALTER TABLE boarding ADD CONSTRAINT FK_114209DA620EA577 FOREIGN KEY (Animal_Key) REFERENCES animal (animalId)');
        $this->addSql('ALTER TABLE boarding ADD CONSTRAINT FK_114209DA74BA3BC0 FOREIGN KEY (Account_Key) REFERENCES account (accountId)');
        $this->addSql('ALTER TABLE donation_m ADD CONSTRAINT FK_7B41703B74BA3BC0 FOREIGN KEY (Account_Key) REFERENCES account (accountId)');
        $this->addSql('ALTER TABLE donation_p ADD CONSTRAINT FK_18471CE274BA3BC0 FOREIGN KEY (Account_Key) REFERENCES account (accountId)');
        $this->addSql('ALTER TABLE donation_product ADD CONSTRAINT FK_F9E6F81D38CC406A FOREIGN KEY (Donation_Key) REFERENCES donation_p (donationPId)');
        $this->addSql('ALTER TABLE donation_product ADD CONSTRAINT FK_F9E6F81DD46E3681 FOREIGN KEY (Product_Key) REFERENCES product (productId)');
        $this->addSql('ALTER TABLE product ADD CONSTRAINT FK_D34A04AD8F2E9BEB FOREIGN KEY (Category_Key) REFERENCES category (categoryId)');
        $this->addSql('ALTER TABLE rapport ADD CONSTRAINT FK_BE34A09C954844CF FOREIGN KEY (Appointment_Key) REFERENCES appointment (appointmentId)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE adoption DROP FOREIGN KEY FK_EDDEB6A974BA3BC0');
        $this->addSql('ALTER TABLE appointment DROP FOREIGN KEY FK_FE38F84474BA3BC0');
        $this->addSql('ALTER TABLE appointment DROP FOREIGN KEY FK_FE38F844620EA577');
        $this->addSql('ALTER TABLE boarding DROP FOREIGN KEY FK_114209DA620EA577');
        $this->addSql('ALTER TABLE boarding DROP FOREIGN KEY FK_114209DA74BA3BC0');
        $this->addSql('ALTER TABLE donation_m DROP FOREIGN KEY FK_7B41703B74BA3BC0');
        $this->addSql('ALTER TABLE donation_p DROP FOREIGN KEY FK_18471CE274BA3BC0');
        $this->addSql('ALTER TABLE donation_product DROP FOREIGN KEY FK_F9E6F81D38CC406A');
        $this->addSql('ALTER TABLE donation_product DROP FOREIGN KEY FK_F9E6F81DD46E3681');
        $this->addSql('ALTER TABLE product DROP FOREIGN KEY FK_D34A04AD8F2E9BEB');
        $this->addSql('ALTER TABLE rapport DROP FOREIGN KEY FK_BE34A09C954844CF');
        $this->addSql('DROP TABLE account');
        $this->addSql('DROP TABLE adoption');
        $this->addSql('DROP TABLE animal');
        $this->addSql('DROP TABLE appointment');
        $this->addSql('DROP TABLE boarding');
        $this->addSql('DROP TABLE cash_register');
        $this->addSql('DROP TABLE category');
        $this->addSql('DROP TABLE donation_m');
        $this->addSql('DROP TABLE donation_p');
        $this->addSql('DROP TABLE donation_product');
        $this->addSql('DROP TABLE product');
        $this->addSql('DROP TABLE rapport');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
