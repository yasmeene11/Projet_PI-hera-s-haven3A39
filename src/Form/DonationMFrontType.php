<?php

namespace App\Form;

use App\Entity\DonationM;
use App\Entity\Account;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;

use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\DataTransformerInterface;
use Doctrine\ORM\EntityManagerInterface;

class DonationMFrontType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Donation_Amount')
            ->add('donationM_Date', DateType::class, [
                'label' => 'Donation Date',
                'widget' => 'single_text',
                'required' => false,
            ]);
        // Ne pas ajouter le champ Account_Key
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationM::class,
        ]);
    }
}