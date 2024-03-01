<?php

namespace App\Form;

use App\Entity\Adoption;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Account;
use App\Entity\Animal;
use Symfony\Component\Form\FormInterface;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class AdoptionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('Adoption_Date', DateType::class, [
            'widget' => 'single_text',
            
        ]);

        if ($options['is_admin']) {
            $builder
                ->add('Adoption_Status', ChoiceType::class, [
                    'choices' => [
                        'Cancelled' => 'Cancelled',
                        'Pending' => 'Pending',
                        'Adopted' => 'Adopted',
                    ],
                    'placeholder' => 'Select Status',
                    'required' => true,
                ])
                ->add('Adoption_Fee')
                ->add('Account_Key', EntityType::class, [
                    'class' => Account::class, 
                    'choice_label' => 'name',
                    'placeholder' => 'Select Account',
                    'required' => true,
                ])
                ;
        } else {
            $builder
                ->add('Adoption_Status', HiddenType::class, [
                    'mapped' => false,
                ])
                ->add('Adoption_Fee', HiddenType::class, [
                    'mapped' => false,
                ])
                ->add('Account_Key', HiddenType::class, [
                    'mapped' => false,
                ])
                ;
        }
    }
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Adoption::class,
           
            'is_admin' => false,
            
            'validation_groups' => function (FormInterface $form) {
                $groups = ['Default'];

                if ($form->getConfig()->getOption('is_admin')) {
                    
                    $groups[] = 'admin';
                }

                return $groups;
            },
        ]);
    }
}