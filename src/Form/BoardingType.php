<?php

namespace App\Form;

use App\Entity\Boarding;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class BoardingType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Start_Date')
            ->add('End_Date')
            ->add('Boarding_Status')
            ->add('Boarding_Fee')
            ->add('Animal_Key')
            ->add('Account_Key')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Boarding::class,
        ]);
    }
}
