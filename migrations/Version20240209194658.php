<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240209194658 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE account (id INT AUTO_INCREMENT NOT NULL, cin INT NOT NULL, user_name VARCHAR(255) NOT NULL, user_surname VARCHAR(255) NOT NULL, gender VARCHAR(255) NOT NULL, phone_number INT NOT NULL, address VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, role VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE adoption (id INT AUTO_INCREMENT NOT NULL, animal_key_id INT NOT NULL, user_key_id INT DEFAULT NULL, cash_register_id INT DEFAULT NULL, adoption_date DATE NOT NULL, adoption_status VARCHAR(255) NOT NULL, adoption_fee DOUBLE PRECISION NOT NULL, UNIQUE INDEX UNIQ_EDDEB6A9B7E590EF (animal_key_id), INDEX IDX_EDDEB6A968464E0E (user_key_id), INDEX IDX_EDDEB6A9A917CC69 (cash_register_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE animal (id INT AUTO_INCREMENT NOT NULL, animal_name VARCHAR(255) NOT NULL, animal_breed VARCHAR(255) NOT NULL, animal_type VARCHAR(255) NOT NULL, animal_status VARCHAR(255) NOT NULL, animal_age INT NOT NULL, enrollement_date DATE NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE appointment (id INT AUTO_INCREMENT NOT NULL, user_key_id INT NOT NULL, animal_key_id INT NOT NULL, appointment_date DATE NOT NULL, appointment_time TIME NOT NULL, appointment_status VARCHAR(255) NOT NULL, INDEX IDX_FE38F84468464E0E (user_key_id), INDEX IDX_FE38F844B7E590EF (animal_key_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE boarding (id INT AUTO_INCREMENT NOT NULL, animal_key_id INT NOT NULL, user_key_id INT NOT NULL, cash_register_id INT DEFAULT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, boarding_fee DOUBLE PRECISION NOT NULL, boarding_status VARCHAR(255) NOT NULL, INDEX IDX_114209DAB7E590EF (animal_key_id), INDEX IDX_114209DA68464E0E (user_key_id), INDEX IDX_114209DAA917CC69 (cash_register_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE cash_register (id INT AUTO_INCREMENT NOT NULL, balance DOUBLE PRECISION NOT NULL, expenses DOUBLE PRECISION NOT NULL, donation_total DOUBLE PRECISION NOT NULL, adoption_fee_total DOUBLE PRECISION NOT NULL, boarding_fee_total DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE category (id INT AUTO_INCREMENT NOT NULL, product_type VARCHAR(255) NOT NULL, product_source VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation (id INT AUTO_INCREMENT NOT NULL, user_key_id INT NOT NULL, donation_date DATE NOT NULL, donation_type VARCHAR(255) NOT NULL, INDEX IDX_31E581A068464E0E (user_key_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation_m (id INT AUTO_INCREMENT NOT NULL, cash_register_id INT DEFAULT NULL, donation_key_id INT NOT NULL, donation_amount DOUBLE PRECISION NOT NULL, INDEX IDX_7B41703BA917CC69 (cash_register_id), INDEX IDX_7B41703BCEC371C8 (donation_key_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE donation_p (id INT AUTO_INCREMENT NOT NULL, donation_key_id INT NOT NULL, donation_product_name VARCHAR(255) NOT NULL, donation_product_quantity INT NOT NULL, donation_product_label VARCHAR(255) NOT NULL, donation_product_expiration_date DATE NOT NULL, INDEX IDX_18471CE2CEC371C8 (donation_key_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE product (id INT AUTO_INCREMENT NOT NULL, category_id INT NOT NULL, product_name VARCHAR(255) NOT NULL, product_quantity INT NOT NULL, product_label VARCHAR(255) NOT NULL, expiration_date DATE NOT NULL, INDEX IDX_D34A04AD12469DE2 (category_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE product_donation_p (product_id INT NOT NULL, donation_p_id INT NOT NULL, INDEX IDX_8B2043C94584665A (product_id), INDEX IDX_8B2043C9C2E84888 (donation_p_id), PRIMARY KEY(product_id, donation_p_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE rapport (id INT AUTO_INCREMENT NOT NULL, appointment_key_id INT NOT NULL, description VARCHAR(1000) NOT NULL, UNIQUE INDEX UNIQ_BE34A09C4A4AEA7D (appointment_key_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE adoption ADD CONSTRAINT FK_EDDEB6A9B7E590EF FOREIGN KEY (animal_key_id) REFERENCES animal (id)');
        $this->addSql('ALTER TABLE adoption ADD CONSTRAINT FK_EDDEB6A968464E0E FOREIGN KEY (user_key_id) REFERENCES account (id)');
        $this->addSql('ALTER TABLE adoption ADD CONSTRAINT FK_EDDEB6A9A917CC69 FOREIGN KEY (cash_register_id) REFERENCES cash_register (id)');
        $this->addSql('ALTER TABLE appointment ADD CONSTRAINT FK_FE38F84468464E0E FOREIGN KEY (user_key_id) REFERENCES account (id)');
        $this->addSql('ALTER TABLE appointment ADD CONSTRAINT FK_FE38F844B7E590EF FOREIGN KEY (animal_key_id) REFERENCES animal (id)');
        $this->addSql('ALTER TABLE boarding ADD CONSTRAINT FK_114209DAB7E590EF FOREIGN KEY (animal_key_id) REFERENCES animal (id)');
        $this->addSql('ALTER TABLE boarding ADD CONSTRAINT FK_114209DA68464E0E FOREIGN KEY (user_key_id) REFERENCES account (id)');
        $this->addSql('ALTER TABLE boarding ADD CONSTRAINT FK_114209DAA917CC69 FOREIGN KEY (cash_register_id) REFERENCES cash_register (id)');
        $this->addSql('ALTER TABLE donation ADD CONSTRAINT FK_31E581A068464E0E FOREIGN KEY (user_key_id) REFERENCES account (id)');
        $this->addSql('ALTER TABLE donation_m ADD CONSTRAINT FK_7B41703BA917CC69 FOREIGN KEY (cash_register_id) REFERENCES cash_register (id)');
        $this->addSql('ALTER TABLE donation_m ADD CONSTRAINT FK_7B41703BCEC371C8 FOREIGN KEY (donation_key_id) REFERENCES donation (id)');
        $this->addSql('ALTER TABLE donation_p ADD CONSTRAINT FK_18471CE2CEC371C8 FOREIGN KEY (donation_key_id) REFERENCES donation (id)');
        $this->addSql('ALTER TABLE product ADD CONSTRAINT FK_D34A04AD12469DE2 FOREIGN KEY (category_id) REFERENCES category (id)');
        $this->addSql('ALTER TABLE product_donation_p ADD CONSTRAINT FK_8B2043C94584665A FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE product_donation_p ADD CONSTRAINT FK_8B2043C9C2E84888 FOREIGN KEY (donation_p_id) REFERENCES donation_p (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE rapport ADD CONSTRAINT FK_BE34A09C4A4AEA7D FOREIGN KEY (appointment_key_id) REFERENCES appointment (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE adoption DROP FOREIGN KEY FK_EDDEB6A9B7E590EF');
        $this->addSql('ALTER TABLE adoption DROP FOREIGN KEY FK_EDDEB6A968464E0E');
        $this->addSql('ALTER TABLE adoption DROP FOREIGN KEY FK_EDDEB6A9A917CC69');
        $this->addSql('ALTER TABLE appointment DROP FOREIGN KEY FK_FE38F84468464E0E');
        $this->addSql('ALTER TABLE appointment DROP FOREIGN KEY FK_FE38F844B7E590EF');
        $this->addSql('ALTER TABLE boarding DROP FOREIGN KEY FK_114209DAB7E590EF');
        $this->addSql('ALTER TABLE boarding DROP FOREIGN KEY FK_114209DA68464E0E');
        $this->addSql('ALTER TABLE boarding DROP FOREIGN KEY FK_114209DAA917CC69');
        $this->addSql('ALTER TABLE donation DROP FOREIGN KEY FK_31E581A068464E0E');
        $this->addSql('ALTER TABLE donation_m DROP FOREIGN KEY FK_7B41703BA917CC69');
        $this->addSql('ALTER TABLE donation_m DROP FOREIGN KEY FK_7B41703BCEC371C8');
        $this->addSql('ALTER TABLE donation_p DROP FOREIGN KEY FK_18471CE2CEC371C8');
        $this->addSql('ALTER TABLE product DROP FOREIGN KEY FK_D34A04AD12469DE2');
        $this->addSql('ALTER TABLE product_donation_p DROP FOREIGN KEY FK_8B2043C94584665A');
        $this->addSql('ALTER TABLE product_donation_p DROP FOREIGN KEY FK_8B2043C9C2E84888');
        $this->addSql('ALTER TABLE rapport DROP FOREIGN KEY FK_BE34A09C4A4AEA7D');
        $this->addSql('DROP TABLE account');
        $this->addSql('DROP TABLE adoption');
        $this->addSql('DROP TABLE animal');
        $this->addSql('DROP TABLE appointment');
        $this->addSql('DROP TABLE boarding');
        $this->addSql('DROP TABLE cash_register');
        $this->addSql('DROP TABLE category');
        $this->addSql('DROP TABLE donation');
        $this->addSql('DROP TABLE donation_m');
        $this->addSql('DROP TABLE donation_p');
        $this->addSql('DROP TABLE product');
        $this->addSql('DROP TABLE product_donation_p');
        $this->addSql('DROP TABLE rapport');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
