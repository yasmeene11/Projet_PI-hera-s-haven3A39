<?php

namespace App\Form;

use App\Entity\DonationP;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class DonationPType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Donation_Product_Name')
            ->add('Donation_Product_Quantity')
            ->add('Donation_Product_Label')
            ->add('Donation_Product_Expiration_Date')
            ->add('DonationP_Date')
            ->add('DonationP_Type')
            ->add('Account_Key')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationP::class,
        ]);
    }
}
