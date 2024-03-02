<?php

namespace App\Form;

use App\Entity\DonationM;
use App\Entity\Account;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class DonationMType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Donation_Amount')
            ->add('donationM_Date', DateType::class, [
                'label' => 'Donation Date',
                'widget' => 'single_text',
                'required' => false,
            ])  
            ->add('Account_Key',EntityType::class, [
                'class' => Account::class,
                'label' => 'Account',
                'required' => false,    
                'placeholder' => '',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationM::class,
        ]);
    }
}